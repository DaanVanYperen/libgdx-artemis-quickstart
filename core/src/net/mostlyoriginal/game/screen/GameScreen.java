package net.mostlyoriginal.game.screen;

import com.artemis.FluidEntityPlugin;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.game.system.ExampleSystem;
import net.mostlyoriginal.plugin.ProfilerPlugin;

/**
 * Example main game screen.
 *
 * @author Daan van Yperen
 */
public class GameScreen implements Screen {

    public static final String BACKGROUND_COLOR_HEX = "969291";

    public static final float MIN_DELTA = 1 / 15f;
    protected World world;

    protected World createWorld() {
        return new World(new WorldConfigurationBuilder()
                // keeps components available until all listeners have been called.
                // Use this if your systems need to access components to clean up after removal.
                .alwaysDelayComponentRemoval(true)
                // Describes dependencies on plugins. You can find more example plugins commented out in build.gradle.
                .dependsOn(
                        //EntityLinkManager.class,
                        //OperationsPlugin.class,
                        ProfilerPlugin.class,
                        FluidEntityPlugin.class)
                .with(
                        // put your own systems here! With the default InvocationStrategy they are called in order each frame.
                        new ExampleSystem()
                ).build());
    }

    @Override
    public void show() {
        if (world == null) {
            world = createWorld();
        }
    }

    @Override
    public void render(float delta) {
        if (world == null) {
            throw new RuntimeException("World not initialized.");
        }
        // Prevent spikes in delta from causing insane world updates.
        world.setDelta(MathUtils.clamp(delta, 0, MIN_DELTA));
        world.process();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }



}
