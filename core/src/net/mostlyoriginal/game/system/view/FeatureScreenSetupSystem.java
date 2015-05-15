package net.mostlyoriginal.game.system.view;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.component.graphics.Color;
import net.mostlyoriginal.api.component.graphics.ColorAnimation;
import net.mostlyoriginal.api.component.graphics.InterpolationStrategy;
import net.mostlyoriginal.api.component.script.Schedule;
import net.mostlyoriginal.api.system.core.PassiveSystem;
import net.mostlyoriginal.game.component.detection.OdbFeatureComponent;
import net.mostlyoriginal.game.screen.GameScreen;
import net.mostlyoriginal.game.system.detection.OdbFeatureDetectionSystem;
import net.mostlyoriginal.game.system.logic.TransitionSystem;

/**
 * @author Daan van Yperen
 */
@Wire
public class FeatureScreenSetupSystem extends PassiveSystem {

	public static final int FEATURE_BORDER_MARGIN = 1;
	public static final Color COLOR_FEATURE_OFF = new Color(1.0f, 1.0f, 1.0f, 0.3f);
	public static final Color COLOR_FEATURE_ON = new Color(1.0f, 1.0f, 1.0f, 1.0f);
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

		assetSystem.createAnimAt(world,
				0,
				0,
				"background",
				Math.max(heightScale, widthScale));
	}

	private void addFeatureIcon(boolean state, String iconId) {

		final float scale = assetSystem.scaleToScreenRounded(0.08f, FeatureScreenAssetSystem.FEATURE_WIDTH);
		final float iconBorderMargin = scale * FEATURE_BORDER_MARGIN;
		final float iconOffset = ((scale * FeatureScreenAssetSystem.FEATURE_WIDTH) + iconBorderMargin);
		final Entity entity = assetSystem.createAnimAt(world,
				(int) (Gdx.graphics.getWidth() - iconOffset * ++iconIndex),
				(int) iconBorderMargin,
				iconId,
				scale);
		entity.edit().add(new Color(COLOR_FEATURE_OFF));

		if (state) {
			entity.edit()
					.add(new Schedule()
							.wait(iconIndex * 0.1f)
							.add(newFeatureOnColorAnimation()));
		}
	}

	private ColorAnimation newFeatureOnColorAnimation() {
		return new ColorAnimation(COLOR_FEATURE_OFF, COLOR_FEATURE_ON, new InterpolationStrategy() {
			@Override
			public float apply(float v1, float v2, float a) {
				return Interpolation.linear.apply(v1, v2, a);
			}
		}, 4.0f, 0.25f);
	}

	public void addLogo() {

		// approximate percentage of screen size with logo. Use rounded numbers to keep the logo crisp.

		assetSystem.createAnimCenteredAt(world,
				FeatureScreenAssetSystem.LOGO_WIDTH,
				FeatureScreenAssetSystem.LOGO_HEIGHT,
				"logo",
				assetSystem.scaleToScreenRounded(0.8f, FeatureScreenAssetSystem.LOGO_WIDTH));
	}

	public static final int DISPLAY_SECONDS = 2;
	private void scheduleTransitionToGameScreen() {
		world.getSystem(TransitionSystem.class).transition(GameScreen.class, DISPLAY_SECONDS);
	}

}
