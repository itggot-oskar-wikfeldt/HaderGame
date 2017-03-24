package me.hsogge.hadergame;

import java.awt.Color;

import me.hsogge.hadergame.state.Menu;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.ui.UIFont;

/**
 * Created by oskar.wikfeldt on 2017-03-21.
 */
public class HaderGame {

    public static Engine engine;

    public static void main(String[] args) {
        engine = new Engine("HaderGame", false, true);
        engine.setState(new Menu(engine));
        engine.start();
    }
}
