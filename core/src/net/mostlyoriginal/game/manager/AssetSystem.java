package net.mostlyoriginal.game.manager;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.api.utils.EntityUtil;

/**
 * @todo Split game logic and library logic.
 * @author Daan van Yperen
 */
@Wire
public class AssetSystem extends net.mostlyoriginal.api.manager.AbstractAssetSystem {

    private TagManager tagManager;

    ComponentMapper<Pos> pm;

    public AssetSystem() {
        super();

        // @todo GAME SPECIFIC, split into library and game specific logic.

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

}
