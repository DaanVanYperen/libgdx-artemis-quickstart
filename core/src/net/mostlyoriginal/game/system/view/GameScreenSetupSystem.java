package net.mostlyoriginal.game.system.view;

import com.artemis.Entity;
import com.artemis.annotations.Wire;
import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.mostlyoriginal.game.util.Anims;

/**
 * @author Daan van Yperen
 */
@Wire
public class GameScreenSetupSystem extends PassiveSystem {

	GameScreenAssetSystem assetSystem;

	@Override
	protected void initialize() {

		Anims.createCenteredAt(world,
				GameScreenAssetSystem.DANCING_MAN_WIDTH,
				GameScreenAssetSystem.DANCING_MAN_HEIGHT,
				"dancingman",
				Anims.scaleToScreenRoundedHeight(0.3f, GameScreenAssetSystem.DANCING_MAN_HEIGHT));

	}

}
