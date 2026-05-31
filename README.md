# hue

Fixes oversaturated colors on Macs with P3 displays when running Minecraft.

Modern Macs render OpenGL in Display P3 by default. Minecraft was designed for sRGB — this agent corrects the window's colorspace at runtime without touching game files.

| Before | After |
| ------ | ----- |
| <img width="854" height="506" alt="image" src="https://github.com/user-attachments/assets/bbf7d31f-b9d5-40e8-b3c1-44030e8ce4e1" /> | <img width="850" height="508" alt="image" src="https://github.com/user-attachments/assets/2f17be77-ab66-4699-9ef2-3742004a91d4" /> |

## Installation

**1. Get the jar for your version**

Download from releases, or build locally (replace module with yours from the [supported versions table](#supported-versions)):
```bash
./gradlew :versions:mc-b181:build
# → versions/mc-b181/build/libs/hue-mc-b181-1.0.0.jar
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

## How it works

Java agent → bytecode-patches `Display.create(PixelFormat)` via ASM, prepending `pf = pf.withAlphaBits(8)`. Requesting an alpha channel causes macOS to composite the window through the sRGB path instead of Display P3. No mod loader dependency.

## Supported versions

**Beta**

| Jar | Version |
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
| `hue-mc-b151` | Beta 1.5_01 |
| `hue-mc-b150` | Beta 1.5 |
| `hue-mc-b141` | Beta 1.4_01 |
| `hue-mc-b140` | Beta 1.4 |
| `hue-mc-b131` | Beta 1.3_01 |
| `hue-mc-b130` | Beta 1.3b |
| `hue-mc-b122` | Beta 1.2_02 |
| `hue-mc-b121` | Beta 1.2_01 |
| `hue-mc-b120` | Beta 1.2 |
| `hue-mc-b112` | Beta 1.1_02 |
| `hue-mc-b111` | Beta 1.1_01 |
| `hue-mc-b102` | Beta 1.0.2 |
| `hue-mc-b101` | Beta 1.0_01 |
| `hue-mc-b100` | Beta 1.0 |

**Alpha**

| Jar | Version |
|---|---|
| `hue-mc-a126` | Alpha 1.2.6 |
| `hue-mc-a125` | Alpha 1.2.5 |
| `hue-mc-a1241` | Alpha 1.2.4_01 |
| `hue-mc-a1234` | Alpha 1.2.3_04 |
| `hue-mc-a1232` | Alpha 1.2.3_02 |
| `hue-mc-a1231` | Alpha 1.2.3_01 |
| `hue-mc-a123` | Alpha 1.2.3 |
| `hue-mc-a122b` | Alpha 1.2.2b |
| `hue-mc-a122a` | Alpha 1.2.2a |
| `hue-mc-a1211` | Alpha 1.2.1_01 |
| `hue-mc-a121` | Alpha 1.2.1 |
| `hue-mc-a1202` | Alpha 1.2.0_02 |
| `hue-mc-a1201` | Alpha 1.2.0_01 |
| `hue-mc-a120` | Alpha 1.2.0 |
| `hue-mc-a1121` | Alpha 1.1.2_01 |
| `hue-mc-a112` | Alpha 1.1.2 |
| `hue-mc-a110` | Alpha 1.1.0 |
