# Audio Improvements - Fabric Port

A **community-maintained port** of [NamelessJu's Audio Improvements Mod](https://github.com/NamelessJu/MC-Audio-Improvements-Mod) for modern Minecraft versions (Fabric).

A client-side Minecraft mod that improves various audio systems.

This checkout targets **Minecraft Java 26.2**, **Fabric Loader 0.19.3 or newer**, and **Java 25**.
Install Fabric API for 26.2 alongside the mod. Mod Menu 20.0.3 is optional; it adds a configuration button.
You can also open the configuration in-game with `/audioimprovements`.

VinURL integration targets **VinURL 2.6.0 for 26.2**. Install VinURL and its own required dependencies separately if you use it.

## Supported Versions

| Minecraft | Branch | Release |
|---|---|---|
| **26.2** | [`codex/26.2-fabric`](https://github.com/usernamelocker/audio-improvements-fork/tree/codex/26.2-fabric) | [`v1.3.4-26.2-fabric`](https://github.com/usernamelocker/audio-improvements-fork/releases/tag/v1.3.4-26.2-fabric) |
| **1.21.11** | [`1.21.11-fabric`](https://github.com/usernamelocker/audio-improvements-fork/tree/1.21.11-fabric) | [`v1.3.2-1.21.11-fabric`](https://github.com/usernamelocker/audio-improvements-fork/releases/tag/v1.3.2-1.21.11-fabric) |
| **26.1.2** | [`26.1.2-fabric`](https://github.com/usernamelocker/audio-improvements-fork/tree/26.1.2-fabric) | [`v1.3.2-26.1.2-fabric`](https://github.com/usernamelocker/audio-improvements-fork/releases/tag/v1.3.2-26.1.2-fabric) |

## Features

- **Doppler Effect** — realistic pitch shifting as sound sources move relative to the player
- **Sound Speed Simulation** — configurable delay for thunder, explosions, and other sounds based on distance
- **Music Clash Prevention** — prevents music discs and note blocks from clashing with background music
- **Mono Audio Controls** — per-category toggle for downmixing sounds to mono
- **Music Frequency Control** — customize how often background music plays
- **Stereo Spatialization Fix** — fixes incorrect stereo panning
- **Customizable Music Disc Range** — adjust the audible range of jukeboxes (supports global range)

## Changes from Original v1.3.2

Music fading, sound delay, and Doppler effects default to **off** in this fork:

| Setting | Original Default | Port Default |
|---|---|---|
| Fade music on disc/note block | ON | OFF |
| Fadeout duration | 2s | 0s (instant) |
| Fadein duration | 5s | 0s (instant) |
| Sound speed (thunder) | 100 blk/s | OFF |
| Sound speed (explosions) | 100 blk/s | OFF |
| Doppler effect intensity | 1.0 | OFF |

## Installation

1. Install Minecraft Java 26.2 with Fabric Loader 0.19.3 or newer and Java 25.
2. Install Fabric API for 26.2 (tested with 0.161.0+26.2).
3. Download `audioimprovements-1.3.4+26.2.jar` from the [26.2 release](https://github.com/usernamelocker/audio-improvements-fork/releases/tag/v1.3.4-26.2-fabric).
4. Replace any older Audio Improvements jar in your client's `mods` folder with this jar.
5. Launch the game and use `/audioimprovements`, or the optional Mod Menu configuration button.

Keep your existing `config/audioimprovements.json` to preserve your settings.
Do not install the `-sources.jar`; it is provided for developers.

## Build

Install a JDK 25 and set `JAVA_HOME` to its directory. The Gradle wrapper downloads Gradle 9.5.1;
you do not need to install Gradle separately.

Windows PowerShell:

```powershell
.\gradlew.bat build
```

Linux/macOS:

```bash
./gradlew build
```

Install `build/libs/audioimprovements-1.3.4+26.2.jar` in your client's `mods` folder.
The `-sources.jar` is for development. This mod is client-only and does not need to be installed on a server.

Use `./gradlew runClient` (Windows: `.\gradlew.bat runClient`) to launch a development client.
Settings are saved to `config/audioimprovements.json` under the game directory; existing settings are retained.

## Development

- `src/client/java`: client initialization, configuration, screens, and audio mixins.
- `src/client/resources`: mixin declarations, including optional VinURL integration.
- `src/main/resources`: mod metadata, translations, and icon.
- `src/test/java`: regression tests; run with the build or `./gradlew test`.
- `gradle.properties`: pinned game, dependency, and mod versions.

See [CHANGELOG.md](CHANGELOG.md) for changes and [TESTING.md](TESTING.md) for runtime checks.
Report problems to [this fork's issue tracker](https://github.com/usernamelocker/audio-improvements-fork/issues).
Please include the Minecraft, Fabric Loader, mod versions, and relevant `logs/latest.log` entries.

## License

MIT — same as the original mod.
