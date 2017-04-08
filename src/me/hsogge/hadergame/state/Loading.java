package me.hsogge.hadergame.state;

import me.hsogge.hadergame.Style;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.graphics.Renderer;
import se.wiklund.haderengine.ui.EnabledUIComponents;
import se.wiklund.haderengine.ui.UILabel;

/**
 * Created by oskar.wikfeldt on 2017-03-24.
 */
public class Loading extends State {

    Engine engine;
    UILabel label = new UILabel("loading...", Style.FONT_BLACK, 64, engine.WIDTH / 2, engine.HEIGHT / 2, true);

    public Loading(Engine engine) {
        EnabledUIComponents.disableAll();

        this.engine = engine;
    }

    int dots = 0;
    double counter = 0;
    double timePassed = 0;

    @Override
    public void update(double v) {
        timePassed += v;

        if (timePassed > 1)
            engine.setState(new Game(engine));

        counter += v;
        if (counter > 0.2) {
            if (dots == 0) {
                label.setText("loading" + ".");
                dots++;
            } else if (dots == 1) {
                label.setText("loading" + "..");
                dots++;
            } else {
                label.setText("loading" + "...");
                dots = 0;
            }
            counter = 0;
        }
        label.update(v);
    }

    @Override
    public void render() {
        label.render();
    }
}
