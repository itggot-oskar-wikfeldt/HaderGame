package me.hsogge.hadergame.math;

/**
 * Created by oskar.wikfeldt on 2017-03-27.
 */
public class Circle {

    private float radius;
    private Vector2f position;

    public Circle(float x, float y, float radius) {
        position = new Vector2f(x, y);
        this.radius = radius;
    }

    public Vector2f getPosition() {
        return position;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
