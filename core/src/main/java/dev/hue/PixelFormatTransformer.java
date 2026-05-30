package dev.hue;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class PixelFormatTransformer implements ClassFileTransformer {

    private static final String DISPLAY_OWNER   = "org/lwjgl/opengl/Display";
    private static final String CREATE_NAME     = "create";
    private static final String CREATE_DESC     = "(Lorg/lwjgl/opengl/PixelFormat;)V";
    private static final String PF_OWNER        = "org/lwjgl/opengl/PixelFormat";
    private static final String WITH_ALPHA      = "withAlphaBits";
    private static final String WITH_ALPHA_DESC = "(I)Lorg/lwjgl/opengl/PixelFormat;";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain domain, byte[] classfileBuffer) {
        if (!DISPLAY_OWNER.equals(className)) return null;
        System.out.println("[Hue] patching Display.create(PixelFormat)");
        try {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(cr, 0);
            DisplayPatcher patcher = new DisplayPatcher(cw);
            cr.accept(patcher, 0);
            if (patcher.patched) {
                System.out.println("[Hue] Display patched");
                return cw.toByteArray();
            }
            System.out.println("[Hue] Display.create(PixelFormat) not found — no patch applied");
        } catch (Throwable t) {
            System.out.println("[Hue] patch error: " + t);
            t.printStackTrace(System.out);
        }
        return null;
    }

    // Prepends `pf = pf.withAlphaBits(8)` to Display.create(PixelFormat).
    private static class DisplayPatcher extends ClassVisitor {
        boolean patched = false;

        DisplayPatcher(ClassVisitor cv) { super(Opcodes.ASM4, cv); }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor,
                                         String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
            if (CREATE_NAME.equals(name) && CREATE_DESC.equals(descriptor)) {
                return new MethodVisitor(Opcodes.ASM4, mv) {
                    @Override
                    public void visitCode() {
                        super.visitCode();
                        visitVarInsn(Opcodes.ALOAD, 0);
                        visitIntInsn(Opcodes.BIPUSH, 8);
                        // 4-param form: compatible with ASM 4 (LaunchWrapper's bundled version)
                        visitMethodInsn(Opcodes.INVOKEVIRTUAL, PF_OWNER,
                                WITH_ALPHA, WITH_ALPHA_DESC);
                        visitVarInsn(Opcodes.ASTORE, 0);
                        patched = true;
                    }
                };
            }
            return mv;
        }
    }
}
