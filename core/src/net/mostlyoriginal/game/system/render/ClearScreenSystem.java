package net.mostlyoriginal.game.system.render;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Responsible for clearing the screen.
 *
 * @author Daan van Yperen
 */
public class ClearScreenSystem extends VoidEntitySystem {
	@Override
	protected void processSystem() {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
