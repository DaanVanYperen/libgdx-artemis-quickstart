package net.mostlyoriginal.game.system.logic;

import com.artemis.Aspect;
import com.artemis.E;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.reflect.ClassReflection;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import net.mostlyoriginal.game.component.logic.Transition;

import static com.artemis.E.*;
import static net.mostlyoriginal.api.operation.OperationFactory.*;
import static net.mostlyoriginal.api.utils.Duration.*;

/**
 * Transition between screens.
 *
 * @author Daan van Yperen
 */
@Wire
public class TransitionSystem extends EntityProcessingSystem {

    private Game game;

    public TransitionSystem(Game game) {
        super(Aspect.all(Transition.class));
        this.game = game;
    }

    /**
     * Transition to screen after delay in seconds.
     */
    public void transition(Class<? extends Screen> screen, float delay) {
        E()
                .script(
                        sequence(
                                delay(seconds(delay)),
                                add(new Transition(screen))
                        )
                );
    }

    @Override
    protected void process(Entity e) {
        try {
            game.setScreen(ClassReflection.newInstance(E(e).transitionScreen()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
