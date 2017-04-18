package me.hsogge.hadergame;

import me.hsogge.hadergame.state.Menu;
import me.hsogge.hadergame.state.Settings;
import se.wiklund.haderengine.Engine;

public class HaderGame {

    public static Engine engine;

    public static void main(String[] args) {
        engine = new Engine("HaderGame", true, true);
        engine.setState(new Menu(engine));
        engine.start();
    }
}
