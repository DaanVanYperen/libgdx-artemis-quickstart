package net.mostlyoriginal.game.system.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.EntityBuilder;
import com.badlogic.gdx.Game;
import net.mostlyoriginal.api.component.script.Schedule;
import net.mostlyoriginal.game.component.logic.Transition;
import net.mostlyoriginal.game.screen.detection.GameScreen;

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
	public void transition(Class<GameScreen> screen, float delay) {
		new EntityBuilder(world).with(new Schedule().wait(delay).add(new Transition(screen)));
	}

	@Override
	protected void process(Entity e) {
		try {
			game.setScreen(mTransition.get(e).screen.newInstance());
		} catch (InstantiationException | IllegalAccessException ex ) {
			throw new RuntimeException(ex);
		}
	}
}
