package net.mostlyoriginal.game.system.view;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.artemis.utils.EntityBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.graphics.Anim;
import net.mostlyoriginal.api.component.graphics.Renderable;
import net.mostlyoriginal.api.manager.AbstractAssetSystem;

/**
 * @author Daan van Yperen
 */
@Wire
public class GameScreenAssetSystem extends AbstractAssetSystem {

	public static final int DANCING_MAN_WIDTH = 24;
	public static final int DANCING_MAN_HEIGHT = 36;

	public GameScreenAssetSystem() {
		super("dancingman.png");
	}

	@Override
	protected void initialize() {
		super.initialize();

		final Animation dancingman = add("dancingman", 0, 0, DANCING_MAN_WIDTH, DANCING_MAN_HEIGHT, 7);
		dancingman.setFrameDuration(1/6f);
	}
}
