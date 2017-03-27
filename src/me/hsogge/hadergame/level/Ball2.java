package me.hsogge.hadergame.level;

import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.maths.Transform;

/**
 * Created by bolle on 2017-03-27.
 */
public class Ball2 extends Instance {

    private Level level;
    private float x, y, width, height;

    private final float GRAVITY = -640;


    public Ball2(Level level, float x, float y) {
        super(new Texture("/ball.png"), x, y, 64, 64);

        this.x = x;
        this.y = y;

        this.width = this.height = 64;

        this.level = level;
    }

    public void update(double delta) {

    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;

        updatePosition();
    }

    private void updatePosition() {
        getTransform().setPosition(x - width / 2, y - height / 2);
    }
}
