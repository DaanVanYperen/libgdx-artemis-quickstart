package net.mostlyoriginal.game;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.managers.UuidEntityManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.system.anim.ColorAnimationSystem;
import net.mostlyoriginal.api.system.camera.CameraShakeSystem;
import net.mostlyoriginal.api.system.camera.CameraSystem;
import net.mostlyoriginal.api.system.camera.EntityCameraSystem;
import net.mostlyoriginal.api.system.interact.AimSystem;
import net.mostlyoriginal.api.system.map.MapCollisionSystem;
import net.mostlyoriginal.api.system.map.MapWallSensorSystem;
import net.mostlyoriginal.api.system.map.TiledMapSystem;
import net.mostlyoriginal.api.system.mouse.MouseCursorSystem;
import net.mostlyoriginal.api.system.physics.*;
import net.mostlyoriginal.api.system.render.AnimRenderSystem;
import net.mostlyoriginal.api.system.render.MapRenderSystem;
import net.mostlyoriginal.api.system.script.EntitySpawnerSystem;
import net.mostlyoriginal.api.system.script.SchedulerSystem;
import net.mostlyoriginal.game.manager.AssetSystem;
import net.mostlyoriginal.game.manager.EntityFactorySystem;
import net.mostlyoriginal.game.system.agent.PlayerControlSystem;
import net.mostlyoriginal.game.system.agent.SlumbererSystem;
import net.mostlyoriginal.game.system.interact.PluckableSystem;

/**
 * @author Daan van Yperen
 */
public class MainScreen implements Screen {

    public static final int CAMERA_ZOOM_FACTOR = 3;
    private final World world;

    public MainScreen() {

        world = new World();

        // @todo comment out systems you do not need for your game.

        // NS2D:
        // @todo port: buildable
        // @todo port: critical (game over when destroyed?)
        // @todo port: Health (Is this sufficiently generic? Probably not.)
        // @todo port: HealthIndicator (Is this sufficiently generic? Probably not.)
        // @todo port: Harvester (Is this sufficiently generic? Probably not.)
        // @todo port: Inventory
        // @todo port: Wallet
        // @todo port: Payload (generalize)
        // @todo port: Weapon (generalize)
        // @todo port: PlayerControlled (Is this sufficiently generic? Probably not.)
        // @todo port: RespawnOnDeath (Is this sufficiently generic? Probably not.)
        // @todo port: SkulkControlled (generalize?)
        // @todo port: Terminal (do we need this? Script suffices).
        // @todo port: example UI system
        // Tox:
        // @todo port: dissolvesontouch.
        // @todo port: EquipBonus
        // @todo port: ExitSystem
        // @todo port: HighscoreSystem
        // Other
        // @todo steve logo!

        /* @todo port:
        // Active - Cleanup
        world.setSystem(new TerminalSystem());

        world.setSystem(new CollisionSystem());

        // Active - Input/Logic
        world.setSystem(new PlayerControlSystem());
        world.setSystem(new SkulkControlSystem());
        world.setSystem(new WeaponSystem());

        // Active - Interactions
        world.setSystem(new BuildableSystem());
        world.setSystem(new CombatSystem());
        world.setSystem(new HarvesterSystem());

        world.setSystem(new BulletCollisionSystem());

        // Active - Render
        world.setSystem(new CostRenderSystem());
        world.setSystem(new HealthRenderSystem());
        world.setSystem(new MapRenderSystemInFront());
        world.setSystem(new DialogRenderSystem());
        world.setSystem(new UIRenderSystem());
        world.setSystem(new UIAlertActiveSpawnerSystem());
        world.setSystem(new UIAlertBuildableUnderAttack());

        world.setSystem(new UIAlertTechpointUnderAttack());
        world.setSystem(new UIStageRenderSystem());
        world.setSystem(new UIStopwatchRenderSytem());

        world.setSystem(new DirectorSystem());
        */

        /** UTILITY - MANAGERS */

        world.setManager(new GroupManager());
        world.setManager(new TagManager());
        world.setManager(new UuidEntityManager());

        /** UTILITY - PASSIVE */

        world.setSystem(new CollisionSystem());
        world.setSystem(new EntityFactorySystem());
        world.setSystem(new TiledMapSystem("level1.tmx"));
        world.setSystem(new AssetSystem());
        world.setSystem(new CameraSystem(CAMERA_ZOOM_FACTOR));

        /** CONTROL */

        // control systems.
        /** Agency Systems (Control and Interact) */
        world.setSystem(new PlayerControlSystem());
        world.setSystem(new SlumbererSystem());

        /** Acting Systems (Control and Interact) */
        world.setSystem(new PluckableSystem());
        world.setSystem(new SchedulerSystem());
        world.setSystem(new EntitySpawnerSystem());

        /** SIMULATE */

        /** Physics systems that apply a vector on an entity */
        world.setSystem(new HomingSystem());
        world.setSystem(new GravitySystem());
        /** Physics systems that constrain the movement*/
        world.setSystem(new MapCollisionSystem());
        world.setSystem(new ClampedSystem());
        /** Physics systems that move the entity to an absolute location. */
        world.setSystem(new AttachmentSystem());
        world.setSystem(new InbetweenSystem());
        world.setSystem(new MouseCursorSystem());
        /** apply velocity */
        world.setSystem(new PhysicsSystem());

        /** Post Physics Simulations */
        world.setSystem(new AimSystem());
        world.setSystem(new MapWallSensorSystem());

        /** PRE-RENDER */

        world.setSystem(new ColorAnimationSystem());

        /** RENDER */

        /** Camera */
        world.setSystem(new EntityCameraSystem());
        world.setSystem(new CameraShakeSystem());

        /** Rendering */
        world.setSystem(new MapRenderSystem());
        world.setSystem(new AnimRenderSystem());

        world.initialize();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
  		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // limit world delta to prevent clipping through walls.
        world.setDelta(MathUtils.clamp(delta, 0, 1 / 15f));
        world.process();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
