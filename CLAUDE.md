# Hue

macOS Display P3 → sRGB colorspace fix for Minecraft. Delivered as a standalone Java agent — no mod loader required.

## Why it exists

Modern Macs default to the Display P3 wide-color gamut. LWJGL creates an NSWindow without explicitly requesting sRGB, so macOS renders OpenGL output in P3 space — colors appear oversaturated vs. the game's intended palette.

Fix: after the game window is created, call `[NSWindow setColorSpace:[NSColorSpace sRGBColorSpace]]` on every window via the ObjC runtime through JNA. The agent is a no-op on non-Mac.

## Project structure

```
core/        — HueAgent (premain), DisplayDetector (SPI), MacColorSpace (JNA/ObjC calls)
mc-b181/     — LWJGL 2 detector (Beta 1.8.1 through ~1.12.2); builds the distributable fat jar
```

## How the SPI works

`HueAgent` uses `ServiceLoader<DisplayDetector>` to find an implementation at runtime. Each `mc-*` module:
1. Implements `DisplayDetector.isReady()` — returns true once the display is up for that version
2. Registers via `META-INF/services/dev.hue.DisplayDetector`
3. Builds a fat jar bundling `core` + itself + JNA; that jar is the drop-in

## Adding a new version

1. Create `mc-<version>/` module
2. Implement `DisplayDetector` — typically polls a static "is display created" method via reflection
3. Register in `src/main/resources/META-INF/services/dev.hue.DisplayDetector`
4. `build.gradle`: depend on `:core`, build fat jar with `Premain-Class: dev.hue.HueAgent`
5. Add to `settings.gradle`

**LWJGL 2** (≤1.12.2): poll `org.lwjgl.opengl.Display.isCreated()`
**LWJGL 3** (≥1.13): poll `org.lwjgl.glfw.GLFW.glfwWindowShouldClose(handle) == false` — requires getting the window handle from `Minecraft.getInstance()` or similar

## Package root

`dev.hue.*`

## Build

```
./gradlew :mc-b181:build
# output: mc-b181/build/libs/hue-mc-b181-1.0.0.jar
```
