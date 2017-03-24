package me.hsogge.hadergame.level;

import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.graphics.Texture;

/**
 * Created by bolle on 2017-03-24.
 */
public class Ball extends Instance {

    private float a = -0.1f;
    private float velY = 0;
    private Level level;

    public Ball(Level level, float x, float y) {
        super(new Texture("/ball.png"), x, y, 64, 64);
        this.level = level;
    }

    public void update(double delta) {
        velY += a;
        move(0, velY);
    }

}
