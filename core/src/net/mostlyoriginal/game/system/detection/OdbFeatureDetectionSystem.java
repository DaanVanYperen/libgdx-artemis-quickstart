package net.mostlyoriginal.game.system.detection;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.EntityBuilder;
import com.artemis.utils.reflect.ClassReflection;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
		super(Aspect.getAspectForAll(OdbFeatureComponent.class));
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
		features.isFactory = isFactoryCreationEnabled();

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		logState("Struct Emulation ...... ", features.isPacked);
		logState("Pooling ............... ", features.isPooled);
		logState("Hotspot Optimization .. ", features.isHotspotOptimization);
		logState("Factory ............... ", features.isFactory);
	}

	private boolean isFactoryCreationEnabled() {
		try {
			return ArtemisUtils.createFactory(world, FactoryTestEntity.class) != null;
		} catch ( Exception e ) {
			// exceptions indicate factories are not being implemented by entity factory.
			return false;
		}
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
		Gdx.app.log("OdbFeatureDetectionSystem", feature + (state ? "enabled" : "disabled"));
	}

	@Override
	protected void process(Entity e) {

		// just for quick debugging on macos.
		OdbFeatureComponent feature = e.getComponent(OdbFeatureComponent.class);
		Gdx.gl.glClearColor(feature.isPooled ? 0 : 1, feature.isPooled ? 1 : 0, 0.25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
