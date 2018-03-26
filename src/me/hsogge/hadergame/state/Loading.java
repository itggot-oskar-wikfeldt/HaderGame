package me.hsogge.hadergame.state;

import me.hsogge.hadergame.level.Level;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.input.InputEnabledViews;

public class Loading extends State {
    Engine engine;
    Level level;
    public Loading(Engine engine) {
        InputEnabledViews.disableAll();
        this.engine = engine;
        this.level = new Level(10000);
    }

    @Override
    public void update(float delta) {
        engine.setState(new Game(engine, level));
    }
}
