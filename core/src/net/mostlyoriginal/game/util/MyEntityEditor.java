package net.mostlyoriginal.game.util;

import net.mostlyoriginal.api.component.Schedule;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.flow.SerialOperation;
import net.mostlyoriginal.api.utils.EntityEditor;

/**
 * @author Daan van Yperen
 */
public class MyEntityEditor extends EntityEditor<MyEntityEditor> {

	private static final MyEntityEditor instance=new MyEntityEditor();
	public static MyEntityEditor instance() {
		return instance;
	}

	public MyEntityEditor schedule(Operation operation) {
		Schedule schedule = add(Schedule.class);
		schedule.operation.add(operation);
		return this;
	}

	public MyEntityEditor schedule(Operation o1, Operation o2) {
		Schedule schedule = add(Schedule.class);
		schedule.operation.add(o1);
		schedule.operation.add(o2);
		return this;
	}
}
