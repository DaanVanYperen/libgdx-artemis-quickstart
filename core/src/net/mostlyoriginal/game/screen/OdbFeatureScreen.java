package net.mostlyoriginal.game.screen;

import com.artemis.World;
import net.mostlyoriginal.api.utils.builder.WorldBuilder;
import net.mostlyoriginal.game.screen.core.WorldScreen;
import net.mostlyoriginal.game.system.ClearScreenSystem;
import net.mostlyoriginal.game.system.detection.OdbFeatureDetectionSystem;

/**
 * Shows all detected artemis-odb features for a couple of seconds.
 *
 * @author Daan van Yperen
 */
public class OdbFeatureScreen extends WorldScreen {

	protected World createWorld() {
		World world = new WorldBuilder()
				.with(
						new ClearScreenSystem(),
						new OdbFeatureDetectionSystem()
				).initialize();
		return world;
	}
}
