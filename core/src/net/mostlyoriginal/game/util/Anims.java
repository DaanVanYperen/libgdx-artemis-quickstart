package net.mostlyoriginal.game.util;

import com.artemis.E;
import com.badlogic.gdx.Gdx;

import static com.artemis.E.E;

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

	public static E createCenteredAt(int width, int height, String animId, float zoom) {
		return createAnimAt(
				(int)((Gdx.graphics.getWidth() / 2) - ((width / 2) * zoom)),
				(int)((Gdx.graphics.getHeight() / 2) - ((height / 2) * zoom)),
				animId,
				zoom);
	}

	public static E createAnimAt(int x, int y, String animId, float scale) {
		return E()
				.renderLayer(0)
				.pos(x, y)
				.anim(animId)
				.scale(scale);
	}
}
