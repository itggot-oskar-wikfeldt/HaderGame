package me.hsogge.hadergame.math;

public class Vector2f {

    private float x, y;

    public Vector2f() {
        this(0, 0);
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(float length, double angle) {
        x = (float) Math.cos(angle) * length;
        y = (float) Math.sin(angle) * length;
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

    public void resize(float dl) {
        double angle = getAngle();
        x += Math.cos(angle) * dl;
        y += Math.sin(angle) * dl;
    }

    public Vector2f scale(double scale) {
        return new Vector2f((float) (x * scale), y * scale);
    }

    public Vector2f add(Vector2f vector) {
        return new Vector2f(x + vector.getX(), y + vector.getY());
    }

    public Vector2f subtract(Vector2f vector) {
        return new Vector2f(x - vector.getX(), y - vector.getY());
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void rotate(double angle) {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        float newX = (float) (cos * x - sin * y);
        float newY = (float) (sin * x + cos * y);
        x = newX;
        y = newY;
    }

    public void setAngle(double angle) {
        rotate(angle - getAngle());
    }

    public double getAngle() {
        if (x > 0)
            return Math.atan(y / x);
        else
            return Math.PI + Math.atan(y / x);
    }

    public double distance(Vector2f vector2f) {
        return Math.sqrt(Math.pow(x - vector2f.getX(), 2) + Math.pow(y - vector2f.getY(), 2));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
