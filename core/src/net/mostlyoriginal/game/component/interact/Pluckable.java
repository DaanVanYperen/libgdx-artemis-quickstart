package net.mostlyoriginal.game.component.interact;

import com.artemis.Component;

/**
 * Can be plucked from the floor!
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.game.system.interact.PluckableSystem
 */
public class Pluckable extends Component {

    public String animPlucked;
    public int layerAfterPlucked = 999;

    public Pluckable(String animPlucked) {
        this.animPlucked = animPlucked;
    }
}
