libgdx-artemis-quickstart
=========================

Quickstart for desktop and html libgdx artemis-odb based jam games.

Contains a very basic game that demos some typical systems and components.

[Play Slumber here](http://www.mostlyoriginal.net/play-slumber/)

### When to use this

If you want artemis-odb with LibGDX's new gradle setup, or you need
some quick ideas for an entry level component/system setup, a fork
will get you up and running quickly.

I use this as my launchpad for jam games. You are welcome to use
it for whatever you need. I might separate the component/systems
into a library later, no guarantees. ;)

### Library Versions

LibGDX 1.3.1 and Artemis-ODB 0.7.1
(LibGDX version can be changed in /build.gradle)

Tested for desktop:dist and html:dist targets. Others targets might function too. ;)

### Organization

net.mostlyoriginal.api contains all generic example components/systems.
net.mostlyoriginal.game contains an example game.

- Entrypoint for your game is ```net.mostlyoriginal.game.MyGame```
- Configure and add systems in ```net.mostlyoriginal.game.MainScreen```
- Resolution can be changed in G.java

### Special note on Reflection

GWT lacks reflection so the build process creates a reflection cache. To use
```@Wire``` make sure your components, managers and systems are placed under
the premade component, manager and system packages.

Artemis does not share libgdx's reflection cache, so if you get gwt reflection
related errors you might need to add classes to Libgdx's cache manually.
Artemis reflection cache errors are prefixed with artemis-odb.

```