package me.hsogge.hadergame.level;

import me.hsogge.hadergame.math.Circle;
import me.hsogge.hadergame.math.Vector2f;
import me.hsogge.hadergame.math.Vector3f;
import org.jetbrains.annotations.Nullable;
import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class Ball extends Instance {

    private Level level;
    private int radius;
    private Vector2f position;

    private Circle circle;

    private final float GRAVITY = 640;
    private final float FRICTION = 70;
    private final float AIR_RESISTANCE = 30;

    private Vector2f vel = new Vector2f(0, 0);

    Ball(Level level, float x, float y, int radius) {
        super(new Texture("/ball.png"), x, y, radius * 2, radius * 2);

        position = new Vector2f(x, y);

        this.radius = radius;

        this.circle = new Circle(position.getX(), position.getY(), radius);

        this.level = level;
    }

    public void update(double delta) {

        shift((float) (vel.getX() * delta), (float) (vel.getY() * delta));

        circle.getPosition().setPos(position.getX(), position.getY());

        Vector3f point = checkCollision();

        if (point != null)
            if (point.getVector2f().distance(circle.getPosition()) < radius - 1)
                position.move((float) (Math.cos(point.getZ() + Math.PI / 2)), (float) (Math.sin(point.getZ() + Math.PI / 2)));

        vel.move(0, (float) (-GRAVITY * delta));

        if (point != null) {

            double velAngle = vel.getAngle();

            if (!(velAngle > point.getZ() && velAngle < point.getZ() + Math.PI)) {
                Vector2f normal = new Vector2f((float) (vel.magnitude() * Math.sin(point.getZ() - velAngle)), (double) point.getZ() + Math.PI / 2);
                vel = vel.add(normal);
            }
            vel.resize((float) (-FRICTION * delta));
        } else {
            vel.resize((float) (-AIR_RESISTANCE * delta));
        }
    }

    @Nullable
    private Vector3f checkCollision() {

        List<Vector3f> intersectingPoints = new ArrayList<>();

        if (!(position.getX() - radius < level.getLimits()[0] || position.getX() + radius >= level.getLimits()[1])) {

            for (int x = (int) (position.getX() - radius); x < position.getX() + radius; x++) {
                Vector3f point = new Vector3f(x, (int) level.getHeight(x), 0);

                if (point.getVector2f().intersects(circle)) {

                    point.setZ((float) Math.atan(level.getGradient((int) point.getX())));

                    intersectingPoints.add(point);

                }
            }
            if (!intersectingPoints.isEmpty())
                return intersectingPoints.get(intersectingPoints.size() / 2);

        }

        return null;

    }

    void shift(float dx, float dy) {
        setPosition(position.getX() + dx, position.getY() + dy);
    }

    void setPosition(float x, float y) {
        position.setPos(x, y);

        updatePosition();
    }

    void setPosition(Vector2f position) {
        setPosition(position.getX(), position.getY());
    }

    void stop() {
        vel.setPos(0, 0);
    }

    void setVel(Vector2f vel) {
        this.vel = vel;
    }

    private void updatePosition() {
        getTransform().setPosition(position.getX() - radius, position.getY() - radius);
    }
}
