package net.mostlyoriginal.api.component.script;

import com.artemis.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import net.mostlyoriginal.api.step.AddStep;
import net.mostlyoriginal.api.step.DeleteFromWorldStep;
import net.mostlyoriginal.api.step.RemoveStep;
import net.mostlyoriginal.api.step.Step;

/**
 * Schedules basic entity transformations.
 *
 * Can be used to schedule things like delayed component addition, entity removal or component removal.
 * Steps are pooled per type.
 *
 * entity.addComponent(new Schedule().wait(0.5f).add(new ExampleComponent()).wait(1.5f).remove(ExampleComponent.class).deleteFromWorld());
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.system.script.SchedulerSystem
 */
public class Schedule extends Component {

    public Array<Step> steps = new Array<Step>(1);

    public float age;
    private float atAge;

    public Schedule() {
    }

    /**
     * Returns a new or pooled action of the specified type.
     */
    static public <T extends Step> T prepare(Class<T> type, float atAge) {
        Pool<T> pool = Pools.get(type);
        T node = pool.obtain();
        node.setPool(pool);
        node.atAge = atAge;
        return node;
    }

    public Schedule wait(float delaySeconds)
    {
        this.atAge += delaySeconds;
        return this;
    }

    public Schedule deleteFromWorld() {
        steps.add(prepare(DeleteFromWorldStep.class, atAge));
        return this;
    }

    public Schedule add( final Component component ) {
        AddStep step = prepare(AddStep.class, atAge);
        step.component = component;
        steps.add(step);
        return this;
    }

    public Schedule remove( final Class<? extends Component> component ) {
        RemoveStep step = prepare(RemoveStep.class, atAge);
        step.componentClass = component;
        steps.add(step);
        return this;
    }
}
