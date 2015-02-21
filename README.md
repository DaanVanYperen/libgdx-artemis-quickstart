libgdx-artemis-quickstart
=========================

Quickstart for libgdx artemis-odb based games.

Preconfigured for artemis-odb advanced features. Contains
a simplistic benchmark game and artemis feature examples.

### When to use this

If you want artemis-odb with weaving, entity factories and 
LibGDX's gradle, a fork will get you up and running quickly.

I use this as my quickstart for jam games. You are welcome 
to use it for whatever you need.

### Library Versions

LibGDX 1.5.4 and Artemis-ODB 0.9

Alter library versions and enable gdx modules in /build.gradle

### Matrix

| Platform        | [odb weaving](https://github.com/junkdog/artemis-odb/wiki/Bytecode-weaving)  | [entity factory](https://github.com/junkdog/artemis-odb/wiki/EntityFactory) | quickstart
|-----------------|----------|----------------|--------------------
| Desktop         | ✓        | ✓ | `gradlew desktop:run`
| Android         | ✓        | ✓ | `gradlew android:installDebug android:run`, launch emulator first.
| iOS             | ✓        | ✓ | `gradlew ios:launchIPhoneSimulator`
| HTML5/GWT       | -        | ✓ | `gradlew html:superDev`, browse to `http://localhost:8080/html`

✓ supported, - not supported

### Weaving

Weaving and entity factory creation are part of the core build step.

### GWT and Reflection

GWT lacks reflection so the build process creates a reflection cache. 

To use `@Wire` make sure your components, managers and systems are 
placed under the premade component, manager and system packages.

There are TWO reflection caches, one for LibGDX, one for Artemis-ODB.
Artemis reflection cache errors are prefixed with `artemis-odb`.

```
