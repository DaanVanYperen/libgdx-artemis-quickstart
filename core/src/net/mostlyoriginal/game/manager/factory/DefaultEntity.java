package net.mostlyoriginal.game.manager.factory;

import com.artemis.EntityFactory;
import com.artemis.annotations.Bind;
import net.mostlyoriginal.api.component.basic.Angle;
import net.mostlyoriginal.api.component.basic.Bounds;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.graphics.Anim;
import net.mostlyoriginal.api.component.map.MapSolid;
import net.mostlyoriginal.api.component.physics.Gravity;
import net.mostlyoriginal.api.component.physics.Physics;

/**
 * Example entity factory
 *
 * To test Artemis-ODB's Annotation Processor.
 *
 * @author Daan van Yperen
 */
@Bind({Pos.class, Angle.class, Bounds.class, Anim.class, MapSolid.class, Physics.class, Gravity.class})
public interface DefaultEntity extends EntityFactory<DefaultEntity> {

	// By convention, method name is mapped to class with same name.
	DefaultEntity pos(float x, float y);

	DefaultEntity bounds(int minx, int miny, int maxx, int maxy);

	DefaultEntity anim(String id);
}
