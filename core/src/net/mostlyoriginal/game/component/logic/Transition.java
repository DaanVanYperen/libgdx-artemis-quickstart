package net.mostlyoriginal.game.component.logic;

import com.artemis.Component;
import com.badlogic.gdx.Screen;

/**
 * Transition helper between screens.
 *
 * @author Daan van Yperen
 */
public class Transition extends Component {
	public Class<? extends Screen> screen;

	public Transition() {
	}

	public Transition(Class<? extends Screen> screen) {
		this.screen = screen;
	}
}
