package net.mostlyoriginal.game;

import com.badlogic.gdx.Game;
import net.mostlyoriginal.game.screen.detection.OdbFeatureScreen;

public class GdxArtemisGame extends Game {

	private static GdxArtemisGame instance;

	@Override
	public void create() {
		instance = this;
		restart();
	}

	public void restart() {
		setScreen(new OdbFeatureScreen());
	}

	public static GdxArtemisGame getInstance()
	{
		return instance;
	}
}
