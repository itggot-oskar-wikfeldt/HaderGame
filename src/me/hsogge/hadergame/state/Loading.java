package me.hsogge.hadergame.state;

import me.hsogge.hadergame.Style;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.input.InputEnabledViews;
import se.wiklund.haderengine.ui.UILabel;

public class Loading extends State {
    UILabel label = new UILabel("loading...", Style.FONT_BLACK, 64, Engine.WIDTH / 2, Engine.HEIGHT / 2, true);
    Engine engine;

    public Loading(Engine engine) {
        InputEnabledViews.disableAll();
        this.engine = engine;

        addSubview(label);

    }

    int dots = 0;
    double counter = 0;
    double timePassed = 0;

    @Override
    public void update(float delta) {
        timePassed += delta;

        if (timePassed > 0)
            engine.setState(new Game(engine));

        counter += delta;
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
        label.update(delta);
    }
}
