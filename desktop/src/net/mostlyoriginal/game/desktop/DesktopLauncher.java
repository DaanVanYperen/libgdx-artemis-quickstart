package net.mostlyoriginal.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.mostlyoriginal.game.G;
import net.mostlyoriginal.game.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.resizable = false;
        config.width  = G.CANVAS_WIDTH;
        config.height = G.CANVAS_HEIGHT;
        config.audioDeviceSimultaneousSources=64;

		new LwjglApplication(new MyGame(), config);
	}
}
