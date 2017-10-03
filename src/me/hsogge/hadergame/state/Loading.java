package me.hsogge.hadergame.state;

import me.hsogge.hadergame.Style;
import me.hsogge.hadergame.level.Level;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.input.InputEnabledViews;
import se.wiklund.haderengine.ui.UILabel;

public class Loading extends State {
    Engine engine;

    public Loading(Engine engine) {
        InputEnabledViews.disableAll();
        this.engine = engine;

    }

    private int timer = 0;

    @Override
    public void update(float delta) {
        if (timer > 1)
            engine.setState(new Game(engine, new Level(19200)));
        timer ++;
    }
}
