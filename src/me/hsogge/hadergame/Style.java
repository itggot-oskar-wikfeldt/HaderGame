package me.hsogge.hadergame;

import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.ui.UIFont;
import se.wiklund.haderengine.ui.UILabel;

import java.awt.*;

/**
 * Created by oskar.wikfeldt on 2017-03-24.
 */
public class Style {

    public static final UIFont FONT = new UIFont("/Abel-Regular.ttf", Color.WHITE, true);
    public static final UIFont FONT_BLACK = new UIFont("/Abel-Regular.ttf", Color.BLACK, true);
    public static final int BTN_WIDTH = 512;
    public static final int BTN_HEIGHT = 128;
    public static final int BTN_MARGIN = 32;
    public static final Texture COLOR_NORMAL = new Texture(0xFF0588a9);
    public static final Texture COLOR_BAD = new Texture(0xFFa90505);
    public static final Texture COLOR_GOOD = new Texture(0xFF05a937);
    public static final UILabel LBL_POWEREDBY = new UILabel("Powered by HaderEngine", FONT_BLACK, 48, 64, 64, false);

}
