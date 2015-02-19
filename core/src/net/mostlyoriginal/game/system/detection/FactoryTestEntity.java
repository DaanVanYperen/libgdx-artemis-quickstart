package net.mostlyoriginal.game.system.detection;

import com.artemis.EntityFactory;
import com.artemis.annotations.Bind;
import net.mostlyoriginal.game.component.detection.OdbFeatureComponent;

/**
 * Used to test if factories are implemented.
 *
 * @author Daan van Yperen
 */
@Bind(OdbFeatureComponent.class)
public interface FactoryTestEntity extends EntityFactory<FactoryTestEntity> {
}
