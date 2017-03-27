package me.hsogge.hadergame.level;

import me.hsogge.hadergame.math.Circle;
import me.hsogge.hadergame.math.Vector2f;
import me.hsogge.hadergame.math.Vector3f;
import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.graphics.Texture;

import java.util.ArrayList;
import java.util.List;
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

        shift(0, -2.0f);


        circle.getPosition().setPos(position.getX(), position.getY());



        checkCollision();

    }

    private void checkCollision() {

        List<Vector3f> points = new ArrayList<>();

        //for (int x = (int) (position.getX() - width / 2); x < position.getX() + width / 2; x++) {
        for (int x = (int) (position.getX() + width / 2); x > position.getX() - width / 2; x--) {
            Vector3f point = new Vector3f(x, (int) level.getHeight(x), 0);

            if (point.getVector2f().intersects(circle)) {

                double angle = Math.atan(level.getGradient((int) point.getX()));

                point.setZ((float) angle);

                points.add(point);

            }
        }

        Vector3f point = points.get(points.size() / 2);

        setPosition((float) (point.getX() + (width/2) * -Math.sin(point.getZ())), (float) (point.getY() + (width/2) * Math.cos(point.getZ())));

    }

    public void shift(float dx, float dy) {
        setPosition(position.getX() + dx, position.getY() + dy);
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
