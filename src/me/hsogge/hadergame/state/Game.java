package me.hsogge.hadergame.state;

import me.hsogge.hadergame.Style;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.ui.UILabel;

public class Game extends State {

    Engine engine;
    UILabel label = new UILabel("playing game !!!", Style.FONT, 256, engine.WIDTH / 2, engine.HEIGHT / 2, true);

    public Game(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void update(double v) {
        label.update(v);
    }

    @Override
    public void render() {
        label.render();
    }
}
