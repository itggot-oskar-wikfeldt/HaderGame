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

    private final float FRICTION = 40;
    private final float AIR_RESISTANCE = 10;

    private Level level;

    private float radius;
    private float minRadius = 8;
    private float maxRadius = 128;
    private Circle circle;
    private Vector2f position;
    private Vector2f vel;

    Ball(Level level, float x, float y, float radius) {
        super(new Texture("/ball.png"), new Transform(x, y, (int) radius * 2, (int) radius * 2));

        position = new Vector2f(x, y);
        vel = new Vector2f(0, 0);

        this.radius = radius;

        this.circle = new Circle(position.getX(), position.getY(), radius);

        this.level = level;
    }

    public void update(float delta) {

        if (shapeShift)
            shapeShift();

        move(vel.getX() * delta, vel.getY() * delta);
        vel.move(0, -level.GRAVITY * delta);

        circle.getPosition().setPos(position.getX(), position.getY());

        // check if there is a point from graph that the ball touches
        Vector3f point = checkCollision();

        // if the ball touches a point
        if (point != null) {
            // distance from ball to point
            double distance = point.getVector2f().distance(circle.getPosition());
            // moves the ball outwards along the point's normal so the distance between them is exactly the ball's radius (the ball just barely doesn't touch the point)
            position.move((float) (-(distance - radius)*(Math.cos(point.getZ() + Math.PI / 2))), (float) (-(distance - radius)*(Math.sin(point.getZ() + Math.PI / 2))));

            // get's the angle which the ball is currently traveling at
            double velAngle = vel.getAngle();

            // adds a vector to the balls velocity which has the angle of the normal of the point that the ball touches
            // the magnitude of the vector is calculated to make the velocity's angle the same as the point's angle
            if (!(velAngle > point.getZ() && velAngle < point.getZ() + Math.PI)) {
                Vector2f normal = new Vector2f((float) (vel.magnitude() * Math.sin(point.getZ() - velAngle)), (double) point.getZ() + Math.PI / 2);
                vel = vel.add(normal);
            }
            // slows the ball down from friction since the ball is touching something
            vel.resize(-FRICTION * delta);
        } else {
            // slows the ball down with air resistance since the ball is not touching anything
            vel.resize(-AIR_RESISTANCE * delta);
        }
    }

    private Vector3f checkCollision() {

        // a list for all the potential points the ball will touch
        List<Vector3f> intersectingPoints = new ArrayList<>();

        // iterates through all the points in the level
        for (Vector4f point : level.getPoints()) {

            // checks if the point is within 5 units if the ball, goes to the next point if it isn't
            if (!(point.getX() - (radius + 5) < position.getX() && point.getX() + (radius + 5) > position.getX() && point.getY() - (radius + 5) < position.getY() && point.getY() + (radius + 5) > position.getY()))
                continue;


            // checks if the point is inside the ball
            if (point.getVector2f().intersects(circle)) {

                Vector3f newPoint = point.getVector3f();

                // sets the third value of the vector3f to the angle of the point. uses the slope of the point to calculate angle
                newPoint.setZ((float) Math.atan(newPoint.getZ()));

                // adds the point to the list of points intersecting with the ball
                intersectingPoints.add(newPoint);

            }

        }
        if (!intersectingPoints.isEmpty())
            // returns the middle point in the list, since the ball is round, the intersecting point found in the middle will be approximately the point you'll want to use
            return intersectingPoints.get(intersectingPoints.size() / 2);

        // returns no point if the ball is not intersecting a point'
        return null;

    }

    private boolean growing;
    private boolean shapeShift = false;

    public void toggleShapeShift() {
        shapeShift = !shapeShift;
    }

    private void shapeShift() {
        if (growing) {
            setRadius(radius + 0.5f);
            move(0, 0.25f);
            if (radius > maxRadius)
                growing = false;
        } else {
            setRadius(radius - 0.5f);
            move(0, -0.25f);
            if (radius < minRadius)
                growing = true;
        }
    }

    public void enlarge() {
        if (radius < maxRadius) {
            setRadius(radius + 0.5f);
            move(0, 0.25f);
        }
    }

    public void shrink() {
        if (radius > minRadius) {
            setRadius(radius - 0.5f);
            move(0, -0.25f);
        }
    }

    void move(float dx, float dy) {
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

    public float getRadius() {
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
