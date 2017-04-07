package me.hsogge.hadergame.level;

import me.hsogge.hadergame.math.Circle;
import me.hsogge.hadergame.math.Vector2f;
import me.hsogge.hadergame.math.Vector3f;
import me.hsogge.hadergame.util.Util;
import org.lwjgl.glfw.GLFW;
import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bolle on 2017-03-27.
 */
public class Ball2 extends Instance {

    private Level level;
    private float width, height;
    private Vector2f position;

    private Circle circle;

    private final float GRAVITY = 640;
    private final float FRICTION = 70;
    private final float AIR_RESISTANCE = 50;

    private Vector2f vel = new Vector2f(0, 0);

    private boolean onGround;

    public Ball2(Level level, float x, float y) {
        super(new Texture("/ball.png"), x, y, 64, 64);

        position = new Vector2f(x, y);

        this.width = this.height = 64;

        this.circle = new Circle(position.getX(), position.getY(), 32);

        this.level = level;
    }

    public void update(double delta) {

        if (Keyboard.isKeyDown(GLFW.GLFW_KEY_SPACE))
            System.out.println("------");

        shift((float) (vel.getX() * delta), (float) (vel.getY() * delta));


        circle.getPosition().setPos(position.getX(), position.getY());

        Vector3f point = checkCollision();

        vel.move(0, (float) (-GRAVITY * delta));


        if (point != null) {

            if (vel.getAngle() < point.getZ()) {
                Vector2f normal = new Vector2f((float) (vel.magnitude() * Math.sin(point.getZ() - vel.getAngle())), (double) point.getZ() + Math.PI / 2);
                vel.add(normal);
            } else {
            }
            System.out.println(vel.getAngle() + "; " + point.getZ());

            vel.resize((float) (-FRICTION * delta));

        }

    }

    private Vector3f checkCollision() {

        List<Vector3f> points = new ArrayList<>();

        onGround = false;

        for (int x = (int) (position.getX() - width / 2); x < position.getX() + width / 2; x++) {
            Vector3f point = new Vector3f(x, (int) level.getHeight(x), 0);

            if (point.getVector2f().intersects(circle)) {

                onGround = true;

                double angle = Math.atan(level.getGradient((int) point.getX()));

                point.setZ((float) angle);

                points.add(point);

            }
        }
        if (!points.isEmpty()) {
            Vector3f point = points.get(points.size() / 2);

            return point;
        }

        return null;

    }

    public void shift(float dx, float dy) {
        setPosition(position.getX() + dx, position.getY() + dy);
    }

    public void setPosition(float x, float y) {
        position.setPos(x, y);

        updatePosition();
    }

    public void stop() {
        vel.setPos(0, 0);
    }

    private void updatePosition() {
        getTransform().setPosition(position.getX() - width / 2, position.getY() - height / 2);
    }
}
