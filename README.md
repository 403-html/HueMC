<div align="center">

# HueMC

**Display P3 → sRGB fix for old Minecraft on Mac**

[![Download](https://img.shields.io/github/v/release/403-html/HueMC?label=download&color=brightgreen)](https://github.com/403-html/HueMC/releases/latest)
[![License](https://img.shields.io/github/license/403-html/HueMC)](LICENSE)

Modern Macs render OpenGL in Display P3 by default. Minecraft was designed for sRGB. This agent corrects the window's colorspace at runtime without touching game files.

| Before | After |
| ------ | ----- |
| <img width="854" height="506" alt="image" src="https://github.com/user-attachments/assets/bbf7d31f-b9d5-40e8-b3c1-44030e8ce4e1" /> | <img width="850" height="508" alt="image" src="https://github.com/user-attachments/assets/2f17be77-ab66-4699-9ef2-3742004a91d4" /> |

</div>

## Installation

**1. Get the jar**

Download `hue-mc-universal-1.0.0.jar` from releases (works for all supported versions), or build locally:
```bash
./gradlew :versions:mc-universal:build
# → versions/mc-universal/build/libs/hue-mc-universal-1.0.0.jar
```

**2. Add to your launcher's JVM arguments**

```
-javaagent:/path/to/hue-mc-universal-1.0.0.jar
```

| Launcher | Where |
|---|---|
| Official launcher | Installations → Edit → More Options → JVM Arguments |
| Prism / MultiMC | Instance → Edit → Settings → Java → Java arguments |
| ATLauncher | Instance → Edit → Java/Minecraft → Java arguments |

Safe to leave in all profiles. No-op on non-Mac and on versions without a detector.

## How it works

Java agent → bytecode-patches `Display.create(PixelFormat)` via ASM, prepending `pf = pf.withAlphaBits(8)`. Requesting an alpha channel causes macOS to composite the window through the sRGB path instead of Display P3. No mod loader dependency.

## Supported versions

All versions listed below use LWJGL 2 with the same `Display.create(PixelFormat)` API, so the same bytecode patch covers all of them. The per-version jars exist but are functionally identical to `hue-mc-universal-1.0.0.jar`; use the universal jar unless you have a specific reason not to.

**Universal**

| Jar | Versions |
|---|---|
| `hue-mc-universal` | rd-132211 to Beta 1.8.1 |

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
| `hue-mc-a10174` | Alpha 1.0.17_04 |
| `hue-mc-a10172` | Alpha 1.0.17_02 |
| `hue-mc-a1016` | Alpha 1.0.16 |
| `hue-mc-a1015` | Alpha 1.0.15 |
| `hue-mc-a1014` | Alpha 1.0.14 |
| `hue-mc-a1011` | Alpha 1.0.11 |
| `hue-mc-a1051` | Alpha 1.0.5_01 |
| `hue-mc-a104` | Alpha 1.0.4 |

**Infdev**

| Jar | Version |
|---|---|
| `hue-mc-inf20100618` | Infdev 20100618 |

**Classic**

| Jar | Version |
|---|---|
| `hue-mc-c03001c` | Classic 0.30_01c |
| `hue-mc-c0013a03` | Classic 0.0.13a_03 |
| `hue-mc-c0013a` | Classic 0.0.13a |
| `hue-mc-c0011a` | Classic 0.0.11a |

**Pre-classic**

| Jar | Version |
|---|---|
| `hue-mc-rd161348` | rd-161348 |
| `hue-mc-rd160052` | rd-160052 |
| `hue-mc-rd20090515` | rd-20090515 |
| `hue-mc-rd132328` | rd-132328 |
| `hue-mc-rd132211` | rd-132211 |
