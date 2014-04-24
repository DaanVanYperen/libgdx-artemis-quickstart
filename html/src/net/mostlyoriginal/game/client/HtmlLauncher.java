package net.mostlyoriginal.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import net.mostlyoriginal.game.G;
import net.mostlyoriginal.game.MyGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(G.CANVAS_WIDTH, G.CANVAS_HEIGHT);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new MyGame();
        }
}