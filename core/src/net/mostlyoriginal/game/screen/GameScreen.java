package net.mostlyoriginal.game.screen;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import net.mostlyoriginal.api.screen.core.WorldScreen;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.api.system.graphics.RenderBatchingSystem;
import net.mostlyoriginal.api.system.render.AnimRenderSystem;
import net.mostlyoriginal.api.system.render.ClearScreenSystem;
import net.mostlyoriginal.api.utils.builder.WorldConfigurationBuilder;
import net.mostlyoriginal.game.system.view.GameScreenAssetSystem;
import net.mostlyoriginal.game.system.view.GameScreenSetupSystem;

/**
 * Example main game screen.
 *
 * @author Daan van Yperen
 */
public class GameScreen extends WorldScreen {

	public static final String BACKGROUND_COLOR_HEX = "969291";

	@Override
	protected World createWorld() {
	return new World(new WorldConfigurationBuilder()
				.with(
						// Replace with your own systems!
						instanceDancingManSystems()
				).build());
	}

	/** Just get a basic dancing man going! */
	private BaseSystem[] instanceDancingManSystems() {
		RenderBatchingSystem renderBatchingSystem;
		return new BaseSystem[]{

				new CameraSystem(1),

				new ClearScreenSystem(Color.valueOf(BACKGROUND_COLOR_HEX)),
				new GameScreenAssetSystem(),
				new GameScreenSetupSystem(),

				renderBatchingSystem = new RenderBatchingSystem(),
				new AnimRenderSystem(renderBatchingSystem),
		};
	}
}
