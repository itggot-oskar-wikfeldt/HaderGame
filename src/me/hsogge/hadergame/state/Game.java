package me.hsogge.hadergame.state;

import me.hsogge.hadergame.Style;
import me.hsogge.hadergame.level.Level;
import org.lwjgl.opengl.GL11;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.ui.UILabel;

public class Game extends State {

    Engine engine;

    Level level;

    public Game(Engine engine) {
        this.engine = engine;

        level = new Level();

    }

    @Override
    public void update(double v) {
        level.update(v);
    }

    @Override
    public void render() {
        GL11.glClearColor(1, 1, 1, 1);

        level.render();
    }
}
