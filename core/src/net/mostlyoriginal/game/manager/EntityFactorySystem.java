package net.mostlyoriginal.game.manager;

import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.component.basic.Angle;
import net.mostlyoriginal.api.component.basic.Bounds;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.component.camera.Camera;
import net.mostlyoriginal.api.component.graphics.Anim;
import net.mostlyoriginal.api.component.map.MapSolid;
import net.mostlyoriginal.api.component.map.MapWallSensor;
import net.mostlyoriginal.api.component.physics.*;
import net.mostlyoriginal.api.component.script.Schedule;
import net.mostlyoriginal.api.manager.AbstractAssetSystem;
import net.mostlyoriginal.api.manager.AbstractEntityFactorySystem;
import net.mostlyoriginal.api.utils.SafeEntityReference;
import net.mostlyoriginal.api.utils.TagEntityReference;
import net.mostlyoriginal.game.MainScreen;
import net.mostlyoriginal.game.component.agent.Slumberer;
import net.mostlyoriginal.game.component.agent.PlayerControlled;
import net.mostlyoriginal.game.component.interact.Pluckable;

/**
 * Game specific entity factory.
 *
 * @todo transform this into a manager.
 * @author Daan van Yperen
 */
@Wire
public class EntityFactorySystem extends AbstractEntityFactorySystem {

    private TagManager tagManager;
    private AbstractAssetSystem abstractAssetSystem;

    @Override
    public Entity createEntity(String entity, int cx, int cy, MapProperties properties) {
        switch (entity) {
            case "player":
                return createPlayer(cx, cy);
            case "slumberer":
                return createSlumberer(cx, cy);
            case "turnip":
                return defaultEntity(cx, cy, "turnip-stuck").add(new Pluckable("turnip-idle"))
                        .add(new Frozen()).getEntity();
            case "chicklet":
                return defaultEntity(cx, cy, "chicklet-stuck")
                        .add(new Pluckable("chicklet-idle"))
                        .add(new Frozen()).getEntity();
            /** @todo Add your entities here */
        }
        return null;
    }

    private Entity createSlumberer(int cx, int cy) {
        Entity slumberer =
                defaultEntity(cx, cy, "slumberer-idle").add(new Slumberer()).getEntity();
        slumberer.getComponent(Anim.class).layer = -2;

        Anim eyeAnim     = new Anim("slumberer-eye", -3);
        eyeAnim.loop     = false;
        Inbetween inbetween = new Inbetween(new SafeEntityReference(slumberer), new TagEntityReference(tagManager, "player"), 0.05f);
        inbetween.ax = 10;
        inbetween.ay = 26;
        inbetween.bx = 10;
        inbetween.by = 10;
        inbetween.maxDistance = 2f;
        Entity eye = world.createEntity().edit()
                .add(new Pos())
                .add(eyeAnim)
                .add(inbetween).getEntity();
        tagManager.register("slumberer-eye", eye);

        Anim eyelidAnim = new Anim("slumberer-eyelid", -1);
        eyelidAnim.loop = false;
        Entity eyelid = world.createEntity().edit()
                .add(new Pos())
                .add(eyelidAnim)
                .add(new Attached(new SafeEntityReference(slumberer), 12, 28)).getEntity();
        tagManager.register("slumberer-eyelid", eyelid);

        return slumberer;
    }

    public Entity createSweat(int x, int y, String animId) {

        final Physics physics = new Physics();
        physics.vx = MathUtils.random(-90, 90)*1.5f;
        physics.vy = MathUtils.random(50, 110)*1.5f;
        physics.friction = 0.1f;

        final TextureRegion frame = abstractAssetSystem.get(animId).getKeyFrame(0);

        return basicCenteredParticle(x, y, animId, 1, 1)
                .add(new Schedule().wait(1f).deleteFromWorld())
                .add(physics)
                .add(new Bounds(frame))
                .add(new Gravity()).getEntity();
    }

    /**
     * Spawns a particle, animation centered on x,y.
     *
     * @param x
     * @param y
     * @param animId
     * @return
     */
    private EntityEdit basicCenteredParticle(int x, int y, String animId, float scale, float speed) {
        Anim anim = new Anim(animId);
        anim.scale=scale;
        anim.speed=speed;
        anim.color.a= 0.9f;

        TextureRegion frame = abstractAssetSystem.get(animId).getKeyFrame(0);

        return world.createEntity().edit()
                .add(new Pos(x - ((frame.getRegionWidth() * anim.scale) / 2), y - (frame.getRegionHeight() * anim.scale) / 2))
                .add(anim);
    }


    private Entity createPlayer(int cx, int cy) {
        Entity player =
                defaultEntity(cx, cy, "player-idle")
                        .add(new PlayerControlled())
                        .add(new MapWallSensor()).getEntity();

        tagManager.register("player", player);

        // now create a drone that will swerve towards the player which contains the camera. this will create a smooth moving camera.
        world.createEntity().edit()
                .add(new Pos(0, 0))
                .add(createCameraBounds())
                .add(new Physics())
                .add(new Homing(new SafeEntityReference(player)))
                .add(new Camera())
                .add(new Clamped(0, 0, 20 * 16, 15 * 16));

        return player;
    }

    private Bounds createCameraBounds() {
        // convert viewport into bounds.
        return new Bounds(
                (-Gdx.graphics.getWidth() / 2) / MainScreen.CAMERA_ZOOM_FACTOR,
                (-Gdx.graphics.getHeight() / 2) / MainScreen.CAMERA_ZOOM_FACTOR,
                (Gdx.graphics.getWidth() / 2) / MainScreen.CAMERA_ZOOM_FACTOR,
                (Gdx.graphics.getHeight() / 2) / MainScreen.CAMERA_ZOOM_FACTOR
        );
    }

    private EntityEdit defaultEntity(int cx, int cy, String startingAnim) {
        return world.createEntity().edit()
                .add(new Pos(cx, cy))
                .add(new Angle())
                .add(new Bounds(0, 0, 25, 16))
                .add(new Anim(startingAnim))
                .add(new MapSolid())
                .add(new Physics())
                .add(new Gravity());
    }

}
