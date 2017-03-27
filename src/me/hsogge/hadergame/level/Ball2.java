package me.hsogge.hadergame.level;

import me.hsogge.hadergame.math.Circle;
import me.hsogge.hadergame.math.Vector2f;
import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.graphics.Texture;

import java.util.Map;

/**
 * Created by bolle on 2017-03-27.
 */
public class Ball2 extends Instance {

    private Level level;
    private float width, height;
    private Vector2f position;

    private Circle circle;

    private final float GRAVITY = -640;

    private float vel = 0;


    public Ball2(Level level, float x, float y) {
        super(new Texture("/ball.png"), x, y, 64, 64);

        position = new Vector2f(x, y);

        this.width = this.height = 64;

        this.circle = new Circle(position.getX(), position.getY(), 32);

        this.level = level;
    }

    public void update(double delta) {

        circle.getPosition().setPos(position.getX(), position.getY());



        checkCollision();

    }

    private void checkCollision() {
        for (int x = (int) (position.getX() - width / 2); x < position.getX() + width / 2; x++) {
            Vector2f point = new Vector2f(x, (int) level.getHeight(x));

            if (point.intersects(circle)) {

                double angle = Math.atan(level.getGradient((int) point.getX()));

                position.setPos(point.getX() + (width/2) * Math.cos(angle), point.getY() + (width/2) * Math.sin(angle));

                break;
            }
        }

    }

    public void setPosition(float x, float y) {
        position.setPos(x, y);

        updatePosition();
    }

    public void stop() {

    }

    private void updatePosition() {
        getTransform().setPosition(position.getX() - width / 2, position.getY() - height / 2);
    }
}
