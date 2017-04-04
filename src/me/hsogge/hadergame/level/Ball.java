package me.hsogge.hadergame.level;

import me.hsogge.hadergame.util.Util;
import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.graphics.Texture;

/**
 * Created by bolle on 2017-03-24.
 */
public class Ball extends Instance {

    private final float GRAVITY = -0.1f;
    private final float ACCELERATION = 0.13f;
    private final float FRICTION = 0.02f;
    private final float AIR_RESISTANCE = 0.002f;
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

    private boolean onGround;

    public void update(double delta) {

        centerX = getTransform().getX() + getTransform().getWidth() / 2;
        centerY = getTransform().getY() + getTransform().getHeight() / 2;

        velY += GRAVITY;
        getTransform().move(velX, velY);

        onGround = getTransform().getY() < level.getHeight((int) centerX);

        if (onGround) {
            velX += ACCELERATION * -level.getGradient((int) centerX);
            velX = Util.decrement(velX, FRICTION);
            double velYFactor = -Math.abs(level.getGradient((int) centerX)) + 1;
            if (velYFactor < 0)
                velYFactor = 0;
            velY -= velY * velYFactor;
            getTransform().setY((float) level.getHeight((int) centerX));
        } else {
            velX = Util.decrement(velX, AIR_RESISTANCE);
        }
    }

    public void setPos(float x, float y) {
        getTransform().setPosition(x, y);
    }

    public void stop() {
        velX = 0;
        velY = 0;
    }

}
