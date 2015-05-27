package net.mostlyoriginal.game.system.view;

import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import net.mostlyoriginal.api.component.graphics.Color;
import net.mostlyoriginal.api.component.graphics.ColorAnimation;
import net.mostlyoriginal.api.component.graphics.InterpolationStrategy;
import net.mostlyoriginal.api.component.script.Schedule;
import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.mostlyoriginal.game.component.detection.OdbFeatureComponent;
import net.mostlyoriginal.game.screen.GameScreen;
import net.mostlyoriginal.game.system.detection.OdbFeatureDetectionSystem;
import net.mostlyoriginal.game.system.logic.TransitionSystem;
import net.mostlyoriginal.game.util.Anims;

/**
 * @author Daan van Yperen
 */
@Wire
public class FeatureScreenSetupSystem extends PassiveSystem {

	public static final int FEATURE_BORDER_MARGIN = 1;
	public static final Color COLOR_FEATURE_FADED = new Color(0.8f, 1.0f, 1.0f, 0.3f);
	public static final Color COLOR_FEATURE_OFF = new Color(0.8f, 1.0f, 1.0f, 0.0f);
	public static final Color COLOR_FEATURE_ON_OFF_COLOR = new Color(0.8f, 1.0f, 1.0f, 1.0f);
	public static final Color COLOR_FEATURE_ON = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Color COLOR_LOGO_FADED = new Color(1.0f, 1.0f, 1.0f, 0.0f);
	public static final Color COLOR_LOGO_FULL = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	FeatureScreenAssetSystem assetSystem;
	TagManager tagManager;

	private int iconIndex;


	@Override
	protected void initialize() {
		super.initialize();

		addBackground();
		addLogo();

		final Entity featureEntity = tagManager.getEntity(OdbFeatureDetectionSystem.FEATURES_TAG);
		final OdbFeatureComponent featureComponent = featureEntity.getComponent(OdbFeatureComponent.class);

		addFeatureIcon(featureComponent.isHotspotOptimization, "feature-hotspot");
		addFeatureIcon(featureComponent.isPacked, "feature-packed");
		addFeatureIcon(featureComponent.isPooled, "feature-pooled");
		addFeatureIcon(featureComponent.isFactory, "feature-factory");

		scheduleTransitionToGameScreen();
	}

	private void addBackground() {

		// scale to fit.
		final float widthScale = Gdx.graphics.getWidth() / FeatureScreenAssetSystem.FEATURE_WIDTH;
		final float heightScale = Gdx.graphics.getHeight() / FeatureScreenAssetSystem.FEATURE_HEIGHT;

		Anims.createAnimAt(world,
				0,
				0,
				"background",
				Math.max(heightScale, widthScale));
	}

	private void addFeatureIcon(boolean state, String iconId) {

		final float scale = Anims.scaleToScreenRounded(0.08f, FeatureScreenAssetSystem.FEATURE_WIDTH);
		final float iconBorderMargin = scale * FEATURE_BORDER_MARGIN;
		final float iconOffset = ((scale * FeatureScreenAssetSystem.FEATURE_WIDTH) + iconBorderMargin);
		final Entity entity = Anims.createAnimAt(world,
				(int) (Gdx.graphics.getWidth() - iconOffset * ++iconIndex),
				(int) iconBorderMargin,
				iconId,
				scale);

		if (state) {
			entity.edit()
					.add(new Color(COLOR_FEATURE_OFF))
					.add(new Schedule()
							.wait(0.5f + iconIndex * 0.1f)
							.add(newFeatureOnColorAnimation(COLOR_FEATURE_OFF, COLOR_FEATURE_ON_OFF_COLOR, 2.0f))
							.wait((1.0f / 2.0f))
							.add(newFeatureOnColorAnimation(COLOR_FEATURE_ON_OFF_COLOR, COLOR_FEATURE_ON, 4.0f))
							.wait((1.0f / 4.0f))
							.remove(ColorAnimation.class));
		} else {
			entity.edit()
					.add(new Color(COLOR_FEATURE_OFF))
					.add(new Schedule()
							.wait(0.5f + iconIndex * 0.1f)
							.add(newFeatureOnColorAnimation(COLOR_FEATURE_OFF, COLOR_FEATURE_FADED, 2.0f))
							.wait((1.0f / 2.0f))
							.remove(ColorAnimation.class));
		}
	}

	private ColorAnimation newFeatureOnColorAnimation(Color colorA, Color colorB, float speed) {
		return new ColorAnimation(colorA, colorB, new InterpolationStrategy() {
			@Override
			public float apply(float v1, float v2, float a) {
				return Interpolation.linear.apply(v1, v2, a);
			}
		}, speed, 1f / speed);
	}

	public void addLogo() {

		// approximate percentage of screen size with logo. Use rounded numbers to keep the logo crisp.

		final Entity entity = Anims.createCenteredAt(world,
				FeatureScreenAssetSystem.LOGO_WIDTH,
				FeatureScreenAssetSystem.LOGO_HEIGHT,
				"logo",
				Anims.scaleToScreenRounded(0.8f, FeatureScreenAssetSystem.LOGO_WIDTH));

		entity.edit()
				.add(new Color(COLOR_LOGO_FADED))
				.add(new Schedule()
						.add(newLogoAppearColorAnimation())
						.wait(0.5f));
	}

	private ColorAnimation newLogoAppearColorAnimation() {
		return new ColorAnimation(COLOR_LOGO_FADED, COLOR_LOGO_FULL, new InterpolationStrategy() {
			@Override
			public float apply(float v1, float v2, float a) {
				return Interpolation.fade.apply(v1, v2, a);
			}
		}, 2f, 0.5f);
	}

	public static final int DISPLAY_SECONDS = 2;

	private void scheduleTransitionToGameScreen() {
		world.getSystem(TransitionSystem.class).transition(GameScreen.class, DISPLAY_SECONDS);
	}

}
