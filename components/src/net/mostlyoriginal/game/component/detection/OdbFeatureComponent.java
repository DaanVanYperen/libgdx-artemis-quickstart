package net.mostlyoriginal.game.component.detection;

import com.artemis.Component;

/**
 * Artemis-odb metadata of active odb features.
 *
 * @see net.mostlyoriginal.game.system.detection.OdbFeatureDetectionSystem
 * @author Daan van Yperen
 */
public class OdbFeatureComponent extends Component {

	/** Components packing supported (on active platform)? */
	public boolean isPacked;

	/** Components pooling supported (on active platform)? */
	public boolean isPooled;

	/** Hotspot optimization applied? */
	public boolean isHotspotOptimization;

	/** Factories supported (on active platform)? */
	public boolean isFactory;
}
