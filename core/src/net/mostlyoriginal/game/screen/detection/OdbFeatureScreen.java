package net.mostlyoriginal.game.screen.detection;

import com.artemis.World;
import net.mostlyoriginal.api.system.script.SchedulerSystem;
import net.mostlyoriginal.api.utils.builder.WorldBuilder;
import net.mostlyoriginal.game.GdxArtemisGame;
import net.mostlyoriginal.game.screen.core.WorldScreen;
import net.mostlyoriginal.game.screen.GameScreen;
import net.mostlyoriginal.game.system.detection.OdbFeatureDetectionSystem;
import net.mostlyoriginal.game.system.logic.TransitionSystem;
import net.mostlyoriginal.game.system.render.ClearScreenSystem;

/**
 * Intro screen that also shows all enabled artemis-odb features for a couple of seconds.
 *
 * @author Daan van Yperen
 */
public class OdbFeatureScreen extends WorldScreen {

	public static final int DISPLAY_SECONDS = 2;

	protected World createWorld() {

		World world = new WorldBuilder()
				.with(
						new ClearScreenSystem(),
						new OdbFeatureDetectionSystem(),
						new SchedulerSystem(),
						new TransitionSystem(GdxArtemisGame.getInstance())
				).initialize();

		// initialize systems.
		world.getSystem(TransitionSystem.class).transition(GameScreen.class, DISPLAY_SECONDS);

		return world;
	}
}
