package me.hsogge.hadergame.state;

import me.hsogge.hadergame.level.Level;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.input.Keyboard;
import se.wiklund.haderengine.ui.EnabledUIComponents;

public class Game extends State {

    Engine engine;

    Level level;

    public Game(Engine engine) {
        EnabledUIComponents.disableAll();

        this.engine = engine;

        level = new Level();

    }

    @Override
    public void update(double v) {

        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            engine.setState(new Menu(engine));
        }

        level.update(v);
    }

    @Override
    public void render() {
        GL11.glClearColor(1, 1, 1, 1);

        level.render();
    }
}
