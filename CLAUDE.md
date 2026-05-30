# Hue

macOS Display P3 → sRGB colorspace fix for Minecraft. Delivered as a standalone Java agent — no mod loader required.

## Why it exists

Modern Macs default to the Display P3 wide-color gamut. LWJGL creates an OpenGL window without requesting an alpha channel, so macOS composites it in P3 space — colors appear oversaturated vs. the game's intended palette.

Fix: intercept `Display.create(PixelFormat)` via ASM bytecode transformation and prepend `pf = pf.withAlphaBits(8)`. Requesting alpha forces macOS to composite the window through the sRGB path.

## Project structure

```
core/        — HueAgent (premain), PixelFormatTransformer (ASM ClassFileTransformer)
mc-b181/     — build module; produces the distributable fat jar (no Java sources, core does all the work)
```

## How the transformer works

`HueAgent.premain` registers `PixelFormatTransformer` as a `ClassFileTransformer`. When the JVM loads `org.lwjgl.opengl.Display`, ASM rewrites `create(PixelFormat)` to prepend `pf = pf.withAlphaBits(8)` before the original body runs.

## Adding a new version

For newer LWJGL versions the entry point changes but the pattern is the same:

1. Create `mc-<version>/` module
2. Add a `ClassFileTransformer` targeting the relevant LWJGL display/window creation method
3. Register it in `HueAgent.premain` (or via a SPI if versions need to be kept separate)
4. `build.gradle`: depend on `:core`, build fat jar with `Premain-Class: dev.hue.HueAgent`
5. Add to `settings.gradle`

**LWJGL 2** (≤1.12.2): patch `org.lwjgl.opengl.Display.create(PixelFormat)`
**LWJGL 3** (≥1.13): equivalent pixel format / window hint lives in GLFW context creation — needs investigation

## Package root

`dev.hue.*`

## Build

```
./gradlew :mc-b181:build
# output: mc-b181/build/libs/hue-mc-b181-1.0.0.jar
```
