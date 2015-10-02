package net.mostlyoriginal.game.util;

import com.artemis.Entity;
import com.artemis.World;
import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.utils.EntityEditor;

/**
 * Local entity editor. Does not support multiple worlds.
 *
 * @author Daan van Yperen
 */
public class E extends EntityEditor<E> {
	private E() {
	}

	private static final E instance=new E();

	public static E create(World world)
	{
		return instance.createEntity(world);
	}

	public static E edit(Entity e)
	{
		return instance.editEntity(e);
	}

	public E schedule(Operation operation) {
		Schedule schedule = add(Schedule.class);
		schedule.operation.add(operation);
		return this;
	}

	public E schedule(Operation o1, Operation o2) {
		Schedule schedule = add(Schedule.class);
		schedule.operation.add(o1);
		schedule.operation.add(o2);
		return this;
	}
}
