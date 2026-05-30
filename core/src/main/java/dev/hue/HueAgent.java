package dev.hue;

import java.lang.instrument.Instrumentation;

public class HueAgent {

    public static void premain(String args, Instrumentation inst) {
        System.out.println("[Hue] agent loaded");
        inst.addTransformer(new PixelFormatTransformer());
    }
}
