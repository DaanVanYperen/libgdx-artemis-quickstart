libgdx-artemis-quickstart
=========================

Quickstart for libgdx artemis-odb based games.

Preconfigured for artemis-odb advanced features. Contains
a simplistic benchmark and artemis feature examples.

### When to use this

If you want artemis-odb with LibGDX's gradle setup, a fork will 
get you up and running quickly.

I use this as my quickstart for jam games. You are welcome to use
it for whatever you need.

### Library Versions

LibGDX 1.5.4 and Artemis-ODB 0.9

Alter versions and re-enable gdx modules in /build.gradle

### Weaving

Weaving is part of a package explosion, and performed on
`desktop:dist` only!

### GWT and Reflection

GWT lacks reflection so the build process creates a reflection cache. 

To use `@Wire` make sure your components, managers and systems are 
placed under the premade component, manager and system packages.

There are TWO reflection caches, one for LibGDX, one for Artemis-ODB.
Artemis reflection cache errors are prefixed with `artemis-odb`.

```