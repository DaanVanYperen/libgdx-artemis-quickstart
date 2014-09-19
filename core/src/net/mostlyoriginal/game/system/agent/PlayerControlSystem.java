package net.mostlyoriginal.game.system.agent;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.component.basic.Angle;
import net.mostlyoriginal.api.component.graphics.Anim;
import net.mostlyoriginal.api.component.map.MapWallSensor;
import net.mostlyoriginal.api.component.physics.Physics;
import net.mostlyoriginal.game.component.agent.PlayerControlled;

/**
 * @author Daan van Yperen
 */
@Wire
public class PlayerControlSystem extends EntityProcessingSystem {

    public static final int MOVEMENT_FACTOR = 1000;
    public static final int JUMP_FACTOR = 20000;

    private ComponentMapper<Anim> am;
    private ComponentMapper<Angle> gm;
    private ComponentMapper<Physics> ym;
    private ComponentMapper<MapWallSensor> sm;
    private float leapStrength;

	@SuppressWarnings("unchecked")
    public PlayerControlSystem() {
        super(Aspect.getAspectForAll(PlayerControlled.class, Physics.class, MapWallSensor.class, Anim.class));
    }

    private void flip(Entity player, boolean flippedX) {
        final Anim anim = am.get(player);
        anim.flippedX = flippedX;
    }

    Vector2 vTmp = new Vector2();

    @Override
    protected void process(Entity player) {

        final Physics physics = ym.get(player);
        final Anim anim = am.get(player);
        final MapWallSensor wm = sm.get(player);

        float dx = 0;
        float dy = 0;

        anim.id = "player-idle";

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            dx = -MOVEMENT_FACTOR;
            flip(player, false);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            dx = MOVEMENT_FACTOR;
            flip(player, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && wm.onFloor) // jump
        {
            leapStrength += world.delta;            
        } else if (leapStrength>0) {
            if ( wm.onFloor ) dy = JUMP_FACTOR * (0.5f + MathUtils.clamp(leapStrength,0f,1f));
            leapStrength = 0;
        };

        anim.speed = 1;
        if (dx != 0) {
            physics.vx += dx * world.delta;
        }

        if (dy != 0) {
            physics.vy += dy * world.delta;
        }

        if ( physics.vy != 0 ) anim.id = "player-leap";
        if ( ((Math.abs(physics.vx) > 5 || (dx != 0)) && physics.vy == 0) )
        {
            anim.id = "player-roll";
            anim.speed = Math.abs(physics.vx*7f) / MOVEMENT_FACTOR;
        } else if ( leapStrength > 0 ) {
            anim.id = "player-prepare-leap";
        }
        //if ( physics.vy < 0 ) anim.id = "player-roll";

        // rotate towards direction while in the air.
        Angle angle = gm.get(player);
        angle.rotation = !wm.onFloor ? vTmp.set(physics.vx, physics.vy).angle() - 90 : 0;
    }
}
