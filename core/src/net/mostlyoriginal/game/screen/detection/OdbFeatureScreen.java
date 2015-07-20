package net.mostlyoriginal.game.screen.detection;

import com.artemis.World;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.Color;
import net.mostlyoriginal.api.screen.core.WorldScreen;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.api.system.graphics.ColorAnimationSystem;
import net.mostlyoriginal.api.system.graphics.RenderBatchingSystem;
import net.mostlyoriginal.api.system.render.AnimRenderSystem;
import net.mostlyoriginal.api.system.render.ClearScreenSystem;
import net.mostlyoriginal.api.system.script.SchedulerSystem;
import net.mostlyoriginal.api.utils.builder.WorldConfigurationBuilder;
import net.mostlyoriginal.game.GdxArtemisGame;
import net.mostlyoriginal.game.system.detection.OdbFeatureDetectionSystem;
import net.mostlyoriginal.game.system.logic.TransitionSystem;
import net.mostlyoriginal.game.system.view.FeatureScreenAssetSystem;
import net.mostlyoriginal.game.system.view.FeatureScreenSetupSystem;

/**
 * Intro screen that also shows all enabled artemis-odb features for a couple of seconds.
 *
 * @author Daan van Yperen
 */
public class OdbFeatureScreen extends WorldScreen {

	protected World createWorld() {

		final RenderBatchingSystem renderBatchingSystem;

		return new World(new WorldConfigurationBuilder()
				.with(
						new TagManager()
				)
				.with(
						// supportive
						new CameraSystem(1),
						new FeatureScreenAssetSystem(),
						new OdbFeatureDetectionSystem()
				).with(
						// processing
						new SchedulerSystem(),
						new TransitionSystem(GdxArtemisGame.getInstance()),
						new ColorAnimationSystem()
				).with(
						// animation
						new ClearScreenSystem(Color.valueOf("969291")),
						renderBatchingSystem = new RenderBatchingSystem(),
						new AnimRenderSystem(renderBatchingSystem),
						new FeatureScreenSetupSystem()
				).build());
	}

}
