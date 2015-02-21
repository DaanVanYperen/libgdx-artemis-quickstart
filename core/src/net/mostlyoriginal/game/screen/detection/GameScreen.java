package net.mostlyoriginal.game.screen.detection;

import com.artemis.World;
import net.mostlyoriginal.api.utils.builder.WorldBuilder;
import net.mostlyoriginal.game.screen.core.WorldScreen;

/**
 * @author Daan van Yperen
 */
public class GameScreen extends WorldScreen {

	@Override
	protected World createWorld() {
		return new WorldBuilder().initialize();
	}
}
