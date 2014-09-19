package net.mostlyoriginal.game.system.interact;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import net.mostlyoriginal.api.component.basic.Bounds;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.graphics.Anim;
import net.mostlyoriginal.api.component.physics.Frozen;
import net.mostlyoriginal.api.component.physics.Physics;
import net.mostlyoriginal.api.system.physics.CollisionSystem;
import net.mostlyoriginal.api.system.physics.PhysicsSystem;
import net.mostlyoriginal.api.system.render.AnimRenderSystem;
import net.mostlyoriginal.api.utils.TagEntityReference;
import net.mostlyoriginal.game.component.interact.Pluckable;
import net.mostlyoriginal.game.manager.EntityFactorySystem;

/**
 * Allows players to pluck entities from the ground.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.game.component.interact.Pluckable
 */
@Wire
public class PluckableSystem extends EntityProcessingSystem {

    CollisionSystem collisionSystem;
    PhysicsSystem physicsSystem;
    AnimRenderSystem animRenderSystem;
    TagManager tagManager;

    ComponentMapper<Pluckable> pm;
    ComponentMapper<Anim> am;
    ComponentMapper<Pos> om;
    ComponentMapper<Bounds> bm;
    ComponentMapper<Physics> phm;

    EntityFactorySystem entityFactorySystem;

    float pluckingCooldown = 0;
    float pluckedCooldown = 0;
    float sweatDropletCooldown = 0;

    public TagEntityReference player;

	@SuppressWarnings("unchecked")
    public PluckableSystem() {
        super(Aspect.getAspectForAll(Pluckable.class, Pos.class, Bounds.class));
    }

    @Override
    protected void initialize() {
        player = new TagEntityReference(tagManager, "player");
    }

    @Override
    protected void begin() {
        super.begin();
        pluckedCooldown-= world.delta;
        if ( pluckedCooldown > 0 )
        {
            Anim playerAnim = am.get(player.get());
            playerAnim.id = "player-plucked";
        }
    }

    @Override
    protected void process(Entity e) {


        // @todo find overlapping actors that are plucking. begin-plucking event, end-plucking event. hooked to controls. depends on player overlap.
        if (player.isActive() && collisionSystem.overlaps(player.get(), e)) {

            if (pluckedCooldown <= 0 && Gdx.input.isKeyPressed(Input.Keys.E)) {

                Anim playerAnim = am.get(player.get());
                playerAnim.id = "player-pluck";

                if (pluckingCooldown == 0) {
                    // begin plucking.
                    pluckingCooldown = 0.8f;
                    sweatDropletCooldown = 0.4f;
                    playerAnim.age=0;
                    playerAnim.speed=1;


                    // stop moving.
                    Physics physics = phm.get(e);
                    physics.vr= physics.vx=physics.vy=0;
                }


                // @todo write this up as a script. Particle spawning should be part of the animation
                sweatDropletCooldown -= world.delta;
                if ( sweatDropletCooldown < 0 )
                {
                    sweatDropletCooldown = 999f;
                    Pos pos = om.get(player.get());
                    Bounds bounds = bm.get(player.get());
                    for (int i=0; i<3; i++)
                    entityFactorySystem.createSweat((int)(pos.x + bounds.maxx * (playerAnim.flippedX ? 0.8f : 0.2f )), (int)(pos.y + bounds.cy()),"particle-sweat");
                }

                // pluckkkkkk
                pluckingCooldown -= world.delta;
                if (pluckingCooldown <= 0) {

                    if (am.has(e)) {
                        Anim pluckableAnim = am.get(e);
                        Pluckable pluckable = pm.get(e);
                        pluckableAnim.id = pluckable.animPlucked;
                        pluckableAnim.layer = pluckable.layerAfterPlucked;
                        animRenderSystem.sortedDirty=true;
                    }

                    pluckedCooldown = 0.4f;

                    physicsSystem.push(e,90,100);
                    physicsSystem.push(player.get(), playerAnim.flippedX ? 180 : 0,80);

                    e.edit().remove(Frozen.class);
	                e.edit().remove(Pluckable.class);
                }

            } else pluckingCooldown = 0;

        }
    }
}
