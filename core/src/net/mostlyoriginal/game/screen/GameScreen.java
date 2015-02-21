package net.mostlyoriginal.game.screen;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import net.mostlyoriginal.api.utils.builder.WorldBuilder;
import net.mostlyoriginal.game.screen.core.WorldScreen;
import net.mostlyoriginal.game.system.render.ClearScreenSystem;

/**
 * Example main game screen.
 *
 * @author Daan van Yperen
 */
public class GameScreen extends WorldScreen {

	@Override
	protected World createWorld() {

		final World world = new WorldBuilder()
				.with(
						new ClearScreenSystem(Color.GREEN)
						// Add your game systems here!
				).initialize();

		// any manual initialization here.

		return world;
	}
}
