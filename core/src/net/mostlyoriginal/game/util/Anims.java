package net.mostlyoriginal.game.util;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.EntityBuilder;
import com.badlogic.gdx.Gdx;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.graphics.Anim;
import net.mostlyoriginal.api.component.graphics.Renderable;

/**
 * @author Daan van Yperen
 */
public class Anims {

	public static int scaleToScreenRounded(float percentageOfScreen, int pixelWidth) {
		return Math.max(1, (int)scaleToScreen(percentageOfScreen, pixelWidth));
	}

	/** Returns pixel perfect zoom that approximates screen percentage. */
	private static float scaleToScreen(float percentageOfScreen, int pixelWidth) {
		return ((Gdx.graphics.getWidth() * percentageOfScreen) / pixelWidth);
	}

	public static int scaleToScreenRoundedHeight(float percentageOfScreen, int pixelHeight) {
		return Math.max(1, (int)scaleToScreen(percentageOfScreen, pixelHeight));
	}

	/** Returns pixel perfect zoom that approximates screen percentage. */
	private static float scaleToScreenHeight(float percentageOfScreen, int pixelHeight) {
		return ((Gdx.graphics.getWidth() * percentageOfScreen) / pixelHeight);
	}

	public static Entity createCenteredAt(World world, int width, int height, String animId, float zoom) {
		return createAnimAt(world,
				(int)((Gdx.graphics.getWidth() / 2) - ((width / 2) * zoom)),
				(int)((Gdx.graphics.getHeight() / 2) - ((height / 2) * zoom)),
				animId,
				zoom);
	}

	public static Entity createAnimAt(World world, int x, int y, String animId, float scale) {

		final Anim anim = new Anim(animId);
		anim.scale= scale;
		return new EntityBuilder(world)
				.with(Renderable.class)
				.with(new Pos(x, y), anim).build();
	}
}
