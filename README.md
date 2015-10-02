libgdx-artemis-quickstart
=========================

[![Build Status](https://travis-ci.org/DaanVanYperen/libgdx-artemis-quickstart.svg)](https://travis-ci.org/DaanVanYperen/libgdx-artemis-quickstart)

Quickstart for libgdx artemis-odb based games.

Preconfigured for artemis-odb advanced features. Contains
a logo screen with artemis feature detection and minimal
game scaffold.

### When to use this

If you want artemis-odb with weaving, entity factories and 
LibGDX's gradle, a fork will get you up and running quickly.

I use this as my quickstart for jam games. You are welcome 
to use it for whatever you need.

### Where to start

Edit `net.mostlyoriginal.game.screen.GameScreen` and start
adding systems!

Running the game displays a odb feature splash screen,
before transitioning to your game.

### Feature detection!

Splash screen icons show available features:

| Icon                  | Platform supports/feature active
|-----------------------|----------------------------------
| Three big squares     | @PackedWeaver
| Tiny squares in a box | @PooledWeaver
| Linked squares        | Hotspot optimization
| Factory icon          | ODB Factories

### Platform cheat sheet

| Platform        | [odb weaving](https://github.com/junkdog/artemis-odb/wiki/Bytecode-weaving)  | [entity factory](https://github.com/junkdog/artemis-odb/wiki/EntityFactory) | quickstart
|-----------------|----------|----------------|--------------------
| Desktop         | ✓        | ✓ | `gradlew desktop:run`
| Android         | ✓        | ✓ | `gradlew android:installDebug android:run`, launch emulator first.
| iOS             | ✓        | ✓ | `gradlew ios:launchIPhoneSimulator`
| HTML5/GWT       | -        | ✓ | `gradlew html:superDev`, browse to `http://localhost:8080/html`

✓ supported, - not supported

### Library Versions

LibGDX 1.7.0, Artemis-odb 1.0.0, Artemis-odb-contrib 1.1.0

Alter library versions and enable gdx modules in /build.gradle

### Weaving

Weaving and entity factory creation are part of the core build step.

Gradle plugin requires Gradle 2.2+!
### Generating component/system matrix

`gradlew generateMatrix`

### GWT and Reflection

GWT lacks reflection so the build process creates a reflection cache. 

To use `@Wire` make sure your components, managers and systems are 
placed under the premade component, manager and system packages.

There are TWO reflection caches, one for LibGDX, one for Artemis-ODB.
Artemis reflection cache errors are prefixed with `artemis-odb`.

```
