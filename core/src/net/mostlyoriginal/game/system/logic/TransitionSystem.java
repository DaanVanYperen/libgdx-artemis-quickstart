package net.mostlyoriginal.game.system.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.EntityBuilder;
import com.artemis.utils.reflect.ClassReflection;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import net.mostlyoriginal.api.component.script.Schedule;
import net.mostlyoriginal.game.component.logic.Transition;

/**
 * Transition between screens.
 *
 * @author Daan van Yperen
 */
@Wire
public class TransitionSystem extends EntityProcessingSystem {

	protected ComponentMapper<Transition> mTransition;
	private Game game;

	public TransitionSystem( Game game ) {
		super(Aspect.all(Transition.class));
		this.game = game;
	}

	/** Transition to screen after delay in seconds. */
	public void transition(Class<? extends Screen> screen, float delay) {
		new EntityBuilder(world).with(new Schedule().wait(delay).add(new Transition(screen)));
	}

	@Override
	protected void process(Entity e) {
		try {
			game.setScreen(ClassReflection.newInstance(mTransition.get(e).screen));
		} catch (Exception ex ) {
			throw new RuntimeException(ex);
		}
	}
}
