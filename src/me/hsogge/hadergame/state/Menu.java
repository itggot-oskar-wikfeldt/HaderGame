package me.hsogge.hadergame.state;

import static me.hsogge.hadergame.Style.*;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.listener.UIButtonListener;

/**
 * Created by oskar.wikfeldt on 2017-03-21.
 */
public class Menu extends State {

    Engine engine;
    UIButtonListener buttonListener;
    UIButton btnPlay;
    UIButton btnSettings;
    UIButton btnQuit;

    public Menu(Engine engine) {
        this.engine = engine;
        int centerX = engine.WIDTH / 2 - BTN_WIDTH/2;
        int centerY = engine.HEIGHT / 2;
        btnPlay = new UIButton("Play", FONT, 48, new Texture(0xFFa98805), centerX, centerY + BTN_HEIGHT/2 + BTN_MARGIN, BTN_WIDTH, BTN_HEIGHT);
        btnSettings = new UIButton("Settings", FONT, 48, new Texture(0xFF0588a9), centerX, centerY - BTN_HEIGHT/2, BTN_WIDTH, BTN_HEIGHT);
        btnQuit = new UIButton("Quit", FONT, 48, new Texture(0xFF0588a9), centerX, centerY - BTN_HEIGHT/2 - BTN_MARGIN - BTN_HEIGHT, BTN_WIDTH, BTN_HEIGHT);
        buttonListener = new UIButtonListener() {
            @Override
            public void onButtonDown(UIButton uiButton, int i) {
            }

            @Override
            public void onButtonUp(UIButton uiButton, int i) {
                if (uiButton == btnPlay) {
                    engine.setState(new Loading(engine));
                } else if (uiButton == btnSettings) {
                    engine.setState(new Settings());
                } else if (uiButton == btnQuit) {
                    engine.exit();
                }
            }
        };
        btnPlay.addButtonListener(buttonListener);
        btnSettings.addButtonListener(buttonListener);
        btnQuit.addButtonListener(buttonListener);


    }

    @Override
    public void update(double v) {
        btnPlay.update(v);
        btnSettings.update(v);
        btnQuit.update(v);
    }

    @Override
    public void render() {
        btnPlay.render();
        btnSettings.render();
        btnQuit.render();
    }
}
