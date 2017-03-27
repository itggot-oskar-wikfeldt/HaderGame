package me.hsogge.hadergame.math;

/**
 * Created by oskar.wikfeldt on 2017-03-27.
 */
public class Vector2f {

    private float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean intersects(Circle circle) {
        return Math.sqrt(Math.pow((x - circle.getPosition().getX()), 2) + Math.pow((y - circle.getPosition().getY()), 2)) < circle.getRadius();
    }

    public void move(float d, float angle) {
        x += d * Math.cos(angle);
        y += d * Math.sin(angle);
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public void normalize() {
        double magnitude = magnitude();

        setPos((float) (x / magnitude), (float) (y / magnitude));

    }

    public void setPos(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
