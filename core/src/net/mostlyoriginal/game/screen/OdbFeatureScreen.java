package net.mostlyoriginal.game.screen;

import com.artemis.World;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.game.system.ClearScreenSystem;

/**
 * Shows all detected artemis-odb features.
 *
 * @author Daan van Yperen
 */
public class OdbFeatureScreen implements Screen {

	public static final float MIN_DELTA = 1 / 15f;

	protected final World world;

	public OdbFeatureScreen() {

		world = new World();
		world.setSystem(new ClearScreenSystem());
		world.initialize();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
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
