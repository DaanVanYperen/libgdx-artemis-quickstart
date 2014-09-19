package net.mostlyoriginal.game.system.agent;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.component.graphics.Anim;
import net.mostlyoriginal.game.component.agent.Slumberer;

/**
 * @author Daan van Yperen
 */
@Wire
public class SlumbererSystem extends EntityProcessingSystem {

    TagManager tagManager;
    ComponentMapper<Slumberer> sm;
    ComponentMapper<Anim> am;
	@SuppressWarnings("unchecked")
    public SlumbererSystem() {
        super(Aspect.getAspectForAll(Slumberer.class));
    }

    @Override
    protected void process(Entity e) {
        Slumberer slumberer = sm.get(e);

        slumberer.blinkCooldown -= world.delta;
        if ( slumberer.blinkCooldown <= 0 )
        {
            slumberer.blinkCooldown = MathUtils.random(3,5);
            slumberer.focusCooldown = 0.3f;

            // reset eyelid animation.
            Anim eyeAnim = am.get(tagManager.getEntity("slumberer-eye"));
            eyeAnim.age=0;
            eyeAnim.speed=0;

            am.get(tagManager.getEntity("slumberer-eyelid")).age=0;

        }

        slumberer.focusCooldown -= world.delta;
        if ( slumberer.focusCooldown <= 0 )
        {
            slumberer.focusCooldown = 999;
            Anim eyeAnim = am.get(tagManager.getEntity("slumberer-eye"));
            eyeAnim.age=0;
            eyeAnim.speed=1;
        }
    }
}
