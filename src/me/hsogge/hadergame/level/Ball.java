package me.hsogge.hadergame.level;

import me.hsogge.hadergame.math.Circle;
import me.hsogge.hadergame.math.Vector2f;
import me.hsogge.hadergame.math.Vector3f;
import me.hsogge.hadergame.math.Vector4f;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.maths.Transform;

import java.util.ArrayList;
import java.util.List;


public class Ball extends View {

    private Level level;
    private float radius;
    private Vector2f position;

    private Circle circle;

    private final float GRAVITY = 640;
    private final float FRICTION = 80;
    private final float AIR_RESISTANCE = 30;

    private Vector2f vel = new Vector2f(0, 0);

    Ball(Level level, float x, float y, float radius) {
        super(new Texture("/ball.png"), new Transform(x, y, (int) radius * 2, (int) radius * 2));

        position = new Vector2f(x, y);

        this.radius = radius;

        this.circle = new Circle(position.getX(), position.getY(), radius);

        this.level = level;
    }

    public void update(float delta) {

        shapeShift();

        shift(vel.getX() * delta, vel.getY() * delta);

        circle.getPosition().setPos(position.getX(), position.getY());

        Vector3f point = checkCollision();

        if (point != null)
            if (point.getVector2f().distance(circle.getPosition()) < radius - 1)
                position.move((float) (Math.cos(point.getZ() + Math.PI / 2)), (float) (Math.sin(point.getZ() + Math.PI / 2)));

        vel.move(0, -GRAVITY * delta);

        if (point != null) {

            double velAngle = vel.getAngle();

            if (!(velAngle > point.getZ() && velAngle < point.getZ() + Math.PI)) {
                Vector2f normal = new Vector2f((float) (vel.magnitude() * Math.sin(point.getZ() - velAngle)), (double) point.getZ() + Math.PI / 2);
                vel = vel.add(normal);
            }
            vel.resize(-FRICTION * delta);
        } else {
            vel.resize(-AIR_RESISTANCE * delta);
        }
    }

    private Vector3f checkCollision() {

        List<Vector3f> intersectingPoints = new ArrayList<>();

        if (!(position.getX() - radius < level.getLimits()[0] || position.getX() + radius >= level.getLimits()[1])) {

            for (Vector4f point : level.getPoints()) {

                if (!(point.getX() - (radius + 5) < position.getX() && point.getX() + (radius + 5) > position.getX() && point.getY() - (radius + 5) < position.getY() && point.getY() + (radius + 5) > position.getY()))
                    continue;

                Vector3f newPoint;

                if (point.getVector2f().intersects(circle)) {

                    newPoint = point.getVector3f();
                    newPoint.setZ((float) Math.atan(newPoint.getZ()));

                    intersectingPoints.add(newPoint);

                }

            }
            if (!intersectingPoints.isEmpty())
                return intersectingPoints.get(intersectingPoints.size() / 2);

        }

        return null;

    }

    private boolean growing;

    private void shapeShift() {
        if (growing) {
            setRadius(radius + 0.5f);
            shift(0, 0.25f);
            if (radius > 180)
                growing = false;
        } else {
            setRadius(radius - 0.5f);
            shift(0, -0.25f);
            if (radius < 8)
                growing = true;
        }
    }

    void shift(float dx, float dy) {
        setPosition(position.getX() + dx, position.getY() + dy);
    }

    void setPosition(float x, float y) {
        position.setPos(x, y);

        updatePosition();
    }

    void setRadius(float radius) {
        this.radius = radius;
        circle.setRadius(radius);
        getTransform().setSize((int) radius * 2, (int) radius * 2);
    }

    float getRadius() {
        return radius;
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
