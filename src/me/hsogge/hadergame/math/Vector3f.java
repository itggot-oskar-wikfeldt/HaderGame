package me.hsogge.hadergame.math;

/**
 * Created by oskar.wikfeldt on 2017-03-27.
 */
public class Vector3f {

    private float x, y, z;
    private Vector2f vector2f;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

        vector2f = new Vector2f(x, y);
    }

    public void setPos(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector2f getVector2f() {
        return vector2f;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
