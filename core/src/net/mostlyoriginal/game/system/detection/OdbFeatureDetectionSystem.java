package net.mostlyoriginal.game.system.detection;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.PackedComponent;
import com.artemis.PooledComponent;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.EntityBuilder;
import com.artemis.utils.reflect.ClassReflection;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import net.mostlyoriginal.game.component.detection.OdbFeatureComponent;
import net.mostlyoriginal.game.component.detection.PackDetectionComponent;
import net.mostlyoriginal.game.component.detection.PoolDetectionComponent;

/**
 * Detect which odb weaving features have been applied.
 *
 * @author Daan van Yperen
 */
@Wire
public class OdbFeatureDetectionSystem extends EntityProcessingSystem {

	public OdbFeatureDetectionSystem() {
		super(Aspect.getEmpty());
	}

	@Override
	protected void initialize() {
		super.initialize();

		OdbFeatureComponent features = new OdbFeatureComponent();
		Entity e = new EntityBuilder(world).with(features).build();

		// detect packing based on reflection.
		features.isPacked = isPackedWeavingEnabled();
		features.isPooled = isPooledWeavingEnabled();
		features.isHotspotOptimization = isHotspotOptimizationEnabled();

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		logState("Struct Emulation", features.isPacked);
		logState("Pooling", features.isPooled);
		logState("Hotspot Optimization", features.isHotspotOptimization);
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

	private boolean isPackedWeavingEnabled() {
		// packed components will subclass PackedComponent.
		return ClassReflection.isAssignableFrom(PackedComponent.class, PackDetectionComponent.class);
	}

	private void logState(final String feature, boolean state) {
		Gdx.app.log("OdbFeatureDetectionSystem", feature + " is " + (state ? "enabled" : "disabled"));
	}

	@Override
	protected void process(Entity e) {
		// do nothing.
	}
}
