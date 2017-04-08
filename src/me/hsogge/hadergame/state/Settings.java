package me.hsogge.hadergame.state;

import me.hsogge.hadergame.component.TextInput;
import me.hsogge.hadergame.component.TextInputListener;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.ui.EnabledUIComponents;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.listener.UIButtonListener;

import static me.hsogge.hadergame.Style.*;

/**
 * Created by oskar.wikfeldt on 2017-03-24.
 */
public class Settings extends State {

    Engine engine;
    UIButton buttonBack;
    TextInput funcInput;
    TextInput gradInput;

    UIButtonListener buttonListener;

    public static String function = "1080 * pow(0.99, x) + 230";
    public static String gradient = "1080 * log(0.99) * pow(0.99, x)";

    public Settings(Engine engine) {
        EnabledUIComponents.disableAll();

        this.engine = engine;

        int centerX = engine.WIDTH / 2 - BTN_WIDTH / 2;
        int numOfButtons = 3;
        int startY = engine.HEIGHT / 2 + (numOfButtons * BTN_HEIGHT / 2 + (numOfButtons-1)*BTN_MARGIN/2);

        funcInput = new TextInput(function, FONT, 48, COLOR_NORMAL, centerX, startY-BTN_HEIGHT, BTN_WIDTH, BTN_HEIGHT);
        gradInput = new TextInput(gradient, FONT, 48, COLOR_NORMAL, centerX, startY - (BTN_HEIGHT + BTN_MARGIN)-BTN_HEIGHT, BTN_WIDTH, BTN_HEIGHT);
        TextInputListener textInputListener = textInput -> {
            if (textInput == funcInput)
                function = funcInput.getText();
            else if (textInput == gradInput)
                gradient = gradInput.getText();
        };

        buttonBack = new UIButton("Back", FONT, 48, COLOR_BAD, centerX, startY - 2*(BTN_HEIGHT + BTN_MARGIN)-BTN_HEIGHT, BTN_WIDTH, BTN_HEIGHT);

        buttonListener = new UIButtonListener() {
            @Override
            public void onButtonDown(UIButton uiButton, int i) {

            }

            @Override
            public void onButtonUp(UIButton uiButton, int i) {
                funcInput.deselect();
                gradInput.deselect();
                if (uiButton == buttonBack) {
                    engine.setState(new Menu(engine));
                } else if (uiButton == funcInput) {
                    funcInput.select();
                } else if (uiButton == gradInput) {
                    gradInput.select();
                }
            }
        };
        buttonBack.addButtonListener(buttonListener);
        funcInput.addButtonListener(buttonListener);
        gradInput.addButtonListener(buttonListener);
        funcInput.setListener(textInputListener);
        gradInput.setListener(textInputListener);

    }

    @Override
    public void update(double v) {
        buttonBack.update(v);
        funcInput.update(v);
        gradInput.update(v);
    }

    @Override
    public void render() {
        buttonBack.render();
        funcInput.render();
        gradInput.render();

        LBL_POWEREDBY.render();
    }
}
