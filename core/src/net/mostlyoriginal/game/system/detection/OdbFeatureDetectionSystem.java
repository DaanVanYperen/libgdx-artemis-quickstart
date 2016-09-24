package net.mostlyoriginal.game.system.detection;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.EntityBuilder;
import com.artemis.utils.reflect.ClassReflection;
import com.badlogic.gdx.Gdx;
import net.mostlyoriginal.game.component.detection.OdbFeatureComponent;
import net.mostlyoriginal.game.component.detection.PoolDetectionComponent;

import static com.artemis.E.*;

/**
 * Detect which odb weaving features have been applied.
 *
 * @author Daan van Yperen
 */
@Wire
public class OdbFeatureDetectionSystem extends BaseSystem {

	public static final String FEATURES_TAG = "features";

	@Override
	protected void initialize() {
		super.initialize();

		OdbFeatureComponent features =
				E().odbFeatureComponent().tag(FEATURES_TAG).getOdbFeatureComponent();

		// detect packing based on reflection.
		features.isPacked = false;
		features.isPooled = isPooledWeavingEnabled();
		features.isHotspotOptimization = isHotspotOptimizationEnabled();
		features.isFactory = false;

		debugFeature("Pooling ............... ", features.isPooled);
		debugFeature("Hotspot Optimization .. ", features.isHotspotOptimization);
	}

	private boolean isHotspotOptimizationEnabled() {
		// hotspot optimization replaces (amongst other steps) references to entityprocessingsystem with entitysystem.
		// so we can determine this optimization by EntityProcessingSystem missing from our system's hierarchy.
		return !ClassReflection.isAssignableFrom(EntityProcessingSystem.class, OdbFeatureDetectionSystem.class);
	}

	private boolean isPooledWeavingEnabled() {
		// pooled components will subclass PooledComponent.
		return ClassReflection.isAssignableFrom(PooledComponent.class, PoolDetectionComponent.class);
	}

	private void debugFeature(final String feature, boolean state) {
		Gdx.app.debug("OdbFeatureDetectionSystem", feature + (state ? "enabled" : "disabled"));
	}

	@Override
	protected void processSystem() {
	}
}
