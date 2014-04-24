package net.mostlyoriginal.game;

import com.badlogic.gdx.Game;

public class MyGame extends Game {
    private static MyGame instance;

    @Override
    public void create() {
        instance = this;
        restart();
    }

    public void restart() {
        setScreen(new MainScreen());
    }

    public static MyGame getInstance()
    {
        return instance;
    }
}
