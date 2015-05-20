package net.mostlyoriginal.game.screen;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import net.mostlyoriginal.api.screen.core.WorldScreen;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.api.system.graphics.RenderBatchingSystem;
import net.mostlyoriginal.api.system.render.AnimRenderSystem;
import net.mostlyoriginal.api.system.render.ClearScreenSystem;
import net.mostlyoriginal.api.utils.builder.WorldBuilder;
import net.mostlyoriginal.game.system.view.GameScreenAssetSystem;
import net.mostlyoriginal.game.system.view.GameScreenSetupSystem;

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
						// Replace with your own systems!
						getDancingManSystems()
				).initialize();

		// any manual initialization here.

		return world;
	}

	/**  */
	private BaseSystem[] getDancingManSystems() {
		RenderBatchingSystem renderBatchingSystem;
		return new BaseSystem[]{

				new CameraSystem(1),

				new ClearScreenSystem(Color.valueOf("969291")),
				new GameScreenAssetSystem(),
				new GameScreenSetupSystem(),

				renderBatchingSystem = new RenderBatchingSystem(),
				new AnimRenderSystem(renderBatchingSystem),
		};
	}
}
