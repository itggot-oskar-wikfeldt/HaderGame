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
        double d = Math.sqrt(Math.pow((x - circle.getPosition().getX()), 2) + Math.pow((y - circle.getPosition().getY()), 2));
        return d < circle.getRadius();
    }

    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public void normalize() {
        double magnitude = magnitude();

        setPos((float) (x / magnitude), (float) (y / magnitude));

    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void rotate(Double angle) {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        this.x = (float) (cos * x - sin * y);
        this.y = (float) (sin * y - cos * x);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
