# Testing

## Port verification (2026-10-26)

- Clean Java 25 build and all five regression tests passed.
- Minecraft 26.2 launched with Fabric Loader 0.19.3 and Fabric API 0.161.0+26.2.
- All four configuration sub-screens rendered, and back navigation worked.
- A second launch with VinURL 2.6.0 and owo-lib 0.13.1+26.2 reached the title screen
  and a new single-player world. `/audioimprovements` opened settings in-game.
- No Audio Improvements mixin failures occurred during these checks.

Audible effect quality, VinURL file playback, and a launch without Mod Menu have not been
manually verified. The checklist below remains the release acceptance checklist.

## Automated checks

Run `./gradlew clean build` with JDK 25 (PowerShell: `.\gradlew.bat clean build`).
This compiles the client, packages resources, and runs regression tests.
Test results are written to `build/reports/tests/test/index.html`.

## Runtime checks

Compilation cannot validate every mixin injection or audible behavior. Before releasing:

1. Launch a Fabric 26.2 client with Fabric API and the built mod. Check `logs/latest.log` for mixin failures.
2. Open settings through Mod Menu and `/audioimprovements`. Visit every sub-screen, resize the window,
   return with Done/Escape, and verify settings survive restarting.
3. Join a disposable single-player world, leave it, and join again. Verify music resumes normally.
4. Enable music fading for discs and note blocks. Start and stop both while background music plays;
   verify fade-in/out and the music volume slider, including mute.
5. Enable custom music frequency, repeat prevention, and menu music controls. Check their behavior
   both in a world and on the title screen.
6. Test disc range and mono toggles at different distances and camera angles. Stop/break jukeboxes
   and note blocks and confirm they no longer suppress background music.
7. Enable Doppler effects and sound delays. Move past sound sources, teleport, and change camera perspective;
   listen for unwanted pitch jumps and check delayed thunder/explosions.
8. Repeat startup and music-block checks with VinURL 2.6.0 for 26.2 and its dependencies installed,
   using a known local audio file. Also verify the mod launches without VinURL or Mod Menu.

Use a separate game directory for testing; the development client uses `run/`.
