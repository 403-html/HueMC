# hue

Fixes oversaturated colors on Macs with P3 displays when running Minecraft.

Modern Macs render OpenGL in Display P3 by default. Minecraft was designed for sRGB — this agent corrects the window's colorspace at runtime without touching game files.

## Installation

**1. Get the jar for your version**

Download from releases, or build locally (replace module with yours from the [supported versions table](#supported-versions)):
```bash
./gradlew :mc-b181:build
# → mc-b181/build/libs/hue-mc-b181-1.0.0.jar
```

**2. Add to your launcher's JVM arguments**

```
-javaagent:/path/to/hue-mc-b181-1.0.0.jar
```

| Launcher | Where |
|---|---|
| Official launcher | Installations → Edit → More Options → JVM Arguments |
| Prism / MultiMC | Instance → Edit → Settings → Java → Java arguments |
| ATLauncher | Instance → Edit → Java/Minecraft → Java arguments |

Safe to leave in all profiles — no-op on non-Mac and on versions without a detector.

## Supported versions

| Jar | Minecraft versions |
|---|---|
| `hue-mc-b181` | Beta 1.8.1 |
| `hue-mc-b180` | Beta 1.8 |
| `hue-mc-b173` | Beta 1.7.3 |
| `hue-mc-b172` | Beta 1.7.2 |
| `hue-mc-b170` | Beta 1.7 |
| `hue-mc-b166` | Beta 1.6.6 |
| `hue-mc-b165` | Beta 1.6.5 |
| `hue-mc-b164` | Beta 1.6.4 |
| `hue-mc-b163` | Beta 1.6.3 |
| `hue-mc-b162` | Beta 1.6.2 |
| `hue-mc-b161` | Beta 1.6.1 |
| `hue-mc-b160` | Beta 1.6 |
| `hue-mc-b151` | Beta 1.5.1 |
| `hue-mc-b150` | Beta 1.5 |

## How it works

Java agent → bytecode-patches `Display.create(PixelFormat)` via ASM, prepending `pf = pf.withAlphaBits(8)`. Requesting an alpha channel causes macOS to composite the window through the sRGB path instead of Display P3. No mod loader dependency.
