package me.hsogge.hadergame.level;

import me.hsogge.hadergame.util.Util;
import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.graphics.Texture;

/**
 * Created by bolle on 2017-03-24.
 */
public class Ball extends Instance {

    private final float GRAVITY = -0.1f;
    private final float ACCELERATION = 0.1f;
    private final float FRICTION = 0.01f;
    private final float AIR_RESISTANCE = 0.001f;
    private float velY = 0;
    private float velX = 0;
    private float centerX;
    private float centerY;
    private Level level;

    public Ball(Level level, float x, float y) {
        super(new Texture("/ball.png"), x, y, 64, 64);
        centerX = getTransform().getX() + getTransform().getWidth() / 2;
        centerY = getTransform().getY() + getTransform().getHeight() / 2;
        this.level = level;
    }

    public void update(double delta) {

        centerX = getTransform().getX() + getTransform().getWidth() / 2;
        centerY = getTransform().getY() + getTransform().getHeight() / 2;

        velY += GRAVITY;
        move(velX, velY);

        if (getTransform().getY() < level.getHeight((int) centerX)) {
            velX += ACCELERATION * -level.getGradient((int) centerX);
            velX = Util.decrement(velX, FRICTION);
            velY = 0;
            getTransform().setY((float) level.getHeight((int) centerX));
        } else {
            velX = Util.decrement(velX, AIR_RESISTANCE);
        }
    }

}
