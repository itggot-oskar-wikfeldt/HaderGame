package me.hsogge.hadergame.state;

import me.hsogge.hadergame.level.Level;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.ui.EnabledUIComponents;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.listener.UIButtonListener;

import static me.hsogge.hadergame.Style.*;

/**
 * Created by oskar.wikfeldt on 2017-03-24.
 */
public class Settings extends State {

    Engine engine;

    UIButton buttonSin;
    UIButton buttonOther;
    UIButton buttonBack;

    UIButtonListener buttonListener;

    public Settings(Engine engine) {
        EnabledUIComponents.disableAll();

        this.engine = engine;

        int centerX = engine.WIDTH / 2 - BTN_WIDTH/2;
        int centerY = engine.HEIGHT / 2;
        buttonSin = new UIButton("y = 100 * sin(x / 100) + 150", FONT, 48, new Texture(0xFFa98805), centerX, centerY + BTN_HEIGHT/2 + BTN_MARGIN, BTN_WIDTH, BTN_HEIGHT);
        buttonOther = new UIButton("y = 2 ^ (-0.01x + 10)", FONT, 48, new Texture(0xFF0588a9), centerX, centerY - BTN_HEIGHT/2, BTN_WIDTH, BTN_HEIGHT);
        buttonBack = new UIButton("Back", FONT, 48, new Texture(0xFF0588a9), centerX, centerY - BTN_HEIGHT/2 - BTN_MARGIN - BTN_HEIGHT, BTN_WIDTH, BTN_HEIGHT);


        buttonListener = new UIButtonListener() {
            @Override
            public void onButtonDown(UIButton uiButton, int i) {

            }

            @Override
            public void onButtonUp(UIButton uiButton, int i) {
                if (uiButton == buttonSin) {
                    Level.setFunctionID(0);
                } else if (uiButton == buttonOther) {
                    Level.setFunctionID(1);
                } else if (uiButton == buttonBack) {
                    engine.setState(new Menu(engine));
                }
            }
        };
        buttonSin.addButtonListener(buttonListener);
        buttonOther.addButtonListener(buttonListener);
        buttonBack.addButtonListener(buttonListener);

    }

    @Override
    public void update(double v) {
        buttonSin.update(v);
        buttonOther.update(v);
        buttonBack.update(v);
    }

    @Override
    public void render() {
        buttonSin.render();
        buttonOther.render();
        buttonBack.render();
    }
}
