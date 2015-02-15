package net.mostlyoriginal.game;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;

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
		world.initialize();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
