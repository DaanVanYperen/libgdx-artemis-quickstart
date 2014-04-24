package net.mostlyoriginal.game.system;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.utils.EntityUtil;

import java.util.HashMap;

/**
 * @todo Split game logic and library logic.
 * @author Daan van Yperen
 */
@Wire
public class AssetSystem extends VoidEntitySystem {

    public Texture tileset;
    public HashMap<String, Animation> sprites = new HashMap<String, Animation>();
    public HashMap<String, Sound> sounds = new HashMap<String, Sound>();
    private TagManager tagManager;

    ComponentMapper<Pos> pm;

    public Animation get(final String identifier) {
        return sprites.get(identifier);
    }

    public Sound getSfx(final String identifier) {
        return sounds.get(identifier);
    }

    public Animation add(final String identifier, int x1, int y1, int w, int h, int repeatX) {
        return add(identifier, x1, y1, w, h, repeatX, 1, tileset);
    }

    public Animation add(final String identifier, int x1, int y1, int w, int h, int repeatX, int repeatY) {
        return add(identifier, x1, y1, w, h, repeatX, repeatY, tileset);
    }

    public Animation add(final String identifier, int x1, int y1, int w, int h, int repeatX, int repeatY, Texture texture) {

        return add(identifier, x1, y1, w, h, repeatX, repeatY, tileset, 0.5f);
    }

    public Animation add(final String identifier, int x1, int y1, int w, int h, int repeatX, int repeatY, Texture texture, float frameDuration) {

        TextureRegion[] regions = new TextureRegion[repeatX*repeatY];

        int count = 0;
        for (int y = 0; y < repeatY; y++) {
            for (int x = 0; x < repeatX; x++) {
                regions[count++] = new TextureRegion(texture, x1 + w * x, y1 + h * y, w, h);
            }
        }

        return sprites.put(identifier, new Animation(frameDuration, regions));
    }

    public AssetSystem() {

        // @todo GAME SPECIFIC, split into library and game specific logic.

        tileset = new Texture("tiles.png");

        add("player-idle", 144, 48, 32, 16, 2);
        add("player-prepare-leap", 144, 16, 32, 32, 1);
        add("player-leap", 144+32, 16, 32, 32, 1);
        add("player-roll", 144, 68, 32, 20, 4,1,tileset,0.1f);
        add("player-nibble", 144, 92, 32, 20, 1);
        add("player-pluck", 272, 16, 32, 24, 2,1,tileset,0.4f);
        add("player-plucked", 240, 68, 32, 20, 1);

        add("chicklet-stuck", 192, 112,  16, 16, 1);
        add("chicklet-waddle", 192+16, 112, 16, 16, 2);
        add("chicklet-idle", 192+32, 112,   16, 16, 1);

        add("turnip-stuck", 144, 144,  16, 16, 1);
        add("turnip-idle", 144+16, 144, 16, 16, 1);

        add("slumberer-idle", 239, 112, 33, 47, 1);
        add("slumberer-eye", 272, 105, 16, 15, 3,1,tileset,1/10f);
        add("slumberer-eyelid", 272, 136, 12, 11, 4,1,tileset,0.1f);

        add("particle-sweat", 144, 128, 8, 8, 2, 1, tileset, 1f);

        loadSounds(new String[] {
        });
    }

    @Override
    protected void processSystem() {
    }


    private void loadSounds(String[] soundnames) {
        for (String identifier : soundnames) {
            sounds.put(identifier, Gdx.audio.newSound(Gdx.files.internal("sfx/" + identifier + ".mp3")));
        }
    }

    private float sfxVolume = 0.2f;
    public void playSfx(String name) {
        if (sfxVolume > 0 )
        {
            Sound sfx = getSfx(name);
            sfx.stop();
            sfx.play(sfxVolume, MathUtils.random(1f, 1.04f), 0);
        }
    }

    public void playSfx(String name, Entity origin) {
        if (sfxVolume > 0 )
        {
            Entity player = tagManager.getEntity("player");
            float distance = EntityUtil.distance(origin, player);

            float volume = sfxVolume - (distance / 2000f);
            if ( volume > 0.01f )
            {
                float balanceX = pm.has(origin) && pm.has(player) ? MathUtils.clamp((pm.get(origin).x - pm.get(player).x) / 100f, -1f, 1f) : 0;
                Sound sfx = getSfx(name);
                sfx.stop();
                sfx.play(volume, MathUtils.random(1f, 1.04f), balanceX);
            }
        }
    }

    public void dispose() {
        sprites.clear();
        tileset.dispose();
        tileset = null;
    }

}
