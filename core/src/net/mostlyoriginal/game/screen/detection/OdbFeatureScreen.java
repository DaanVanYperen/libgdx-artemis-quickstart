package net.mostlyoriginal.game.screen.detection;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import net.mostlyoriginal.api.screen.core.WorldScreen;
import net.mostlyoriginal.api.system.render.ClearScreenSystem;
import net.mostlyoriginal.api.system.script.SchedulerSystem;
import net.mostlyoriginal.api.utils.builder.WorldBuilder;
import net.mostlyoriginal.game.GdxArtemisGame;
import net.mostlyoriginal.game.screen.GameScreen;
import net.mostlyoriginal.game.system.detection.OdbFeatureDetectionSystem;
import net.mostlyoriginal.game.system.logic.TransitionSystem;

/**
 * Intro screen that also shows all enabled artemis-odb features for a couple of seconds.
 *
 * @author Daan van Yperen
 */
public class OdbFeatureScreen extends WorldScreen {

	protected World createWorld() {

		World world = new WorldBuilder()
				.with(
						new ClearScreenSystem(Color.valueOf("231f20")),
						new OdbFeatureDetectionSystem(),
						new SchedulerSystem(),
						new TransitionSystem(GdxArtemisGame.getInstance())
				).initialize();

		// initialize systems.
		scheduleTransitionToGameScreen(world);

		return world;
	}

	public static final int DISPLAY_SECONDS = 2;
	private void scheduleTransitionToGameScreen(World world) {
		world.getSystem(TransitionSystem.class).transition(GameScreen.class, DISPLAY_SECONDS);
	}
}
