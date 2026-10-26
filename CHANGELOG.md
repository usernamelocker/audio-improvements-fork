# Changelog

## 1.3.4+26.2

- Target Minecraft Java 26.2 with Java 25, Fabric API 0.161.0+26.2, and Mod Menu 20.0.3.
- Update screen navigation to the 26.2 GUI API.
- Update camera access and correct music-delay injection points so custom frequency controls the per-tick delay clamp.
- Pin Loom 1.17.21 and Gradle 9.5.1 for reproducible builds.
- Update optional VinURL integration to its 2.6.0 release for 26.2 and the current sound playback signature.
- Preserve the 1.3.3 fix that keeps fading music consistent with the music volume slider.
- Remove stopped music-block channels before clearing their type.
- Respect the current FloatBuffer position when tracking direct OpenAL position changes.
- Use the correct mono frame size and byte order when downmixing signed 16-bit stereo audio.
- Close configuration readers, create missing configuration directories, and persist repaired invalid entries.
- Derive Minecraft and loader constraints from build properties and direct support links to this fork.

## 1.3.3

- Fix music fading ignoring the music volume slider.
