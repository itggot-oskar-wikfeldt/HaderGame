package me.hsogge.hadergame.state;

import me.hsogge.hadergame.Style;
import org.lwjgl.opengl.GL11;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.input.InputEnabledViews;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.UILabel;
import se.wiklund.haderengine.ui.listener.UIButtonListener;

import static me.hsogge.hadergame.Style.*;

/**
 * Created by oskar.wikfeldt on 2017-03-21.
 */
public class Menu extends State {

    Engine engine;
    UIButtonListener buttonListener;
    UIButton btnPlay;
    UIButton btnSettings;
    UIButton btnQuit;

    UILabel title = new UILabel("hsogge game", Style.FONT_BLACK, 128, engine.WIDTH / 2, engine.HEIGHT - 128, true);

    public Menu(Engine engine) {
        InputEnabledViews.disableAll();

        GL11.glClearColor(1, 1, 1, 1);

        this.engine = engine;
        int centerX = engine.WIDTH / 2 - BTN_WIDTH / 2;
        int centerY = engine.HEIGHT / 2;
        btnPlay = new UIButton("Run", FONT, 48, COLOR_GOOD, new Transform(centerX, centerY + BTN_HEIGHT / 2 + BTN_MARGIN, BTN_WIDTH, BTN_HEIGHT));
        btnSettings = new UIButton("Functions", FONT, 48, COLOR_NORMAL, new Transform(centerX, centerY - BTN_HEIGHT / 2, BTN_WIDTH, BTN_HEIGHT));
        btnQuit = new UIButton("Quit", FONT, 48, COLOR_BAD, new Transform(centerX, centerY - BTN_HEIGHT / 2 - BTN_MARGIN - BTN_HEIGHT, BTN_WIDTH, BTN_HEIGHT));
        buttonListener = new UIButtonListener() {
            @Override
            public void onButtonDown(UIButton uiButton, int i) {
            }

            @Override
            public void onButtonUp(UIButton uiButton, int i) {
                if (uiButton == btnPlay) {
                    engine.setState(new Loading(engine));
                } else if (uiButton == btnSettings) {
                    engine.setState(new Settings(engine));
                } else if (uiButton == btnQuit) {
                    engine.exit();
                }
            }
        };
        btnPlay.addButtonListener(buttonListener);
        btnSettings.addButtonListener(buttonListener);
        btnQuit.addButtonListener(buttonListener);

        addSubview(btnPlay);
        addSubview(btnSettings);
        addSubview(btnQuit);
        addSubview(title);
        addSubview(LBL_POWEREDBY);
    }

    @Override
    public void update(float delta) {
        btnPlay.update(delta);
        btnSettings.update(delta);
        btnQuit.update(delta);
    }

}
