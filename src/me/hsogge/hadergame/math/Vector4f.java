package me.hsogge.hadergame.math;

/**
 * Created by bolle on 2017-04-13.
 */
public class Vector4f {

    private float x, y, z, a;

    public Vector4f(float x, float y, float z, float a) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.a = a;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public Vector3f getVector3f() {
        return new Vector3f(x, y, z);
    }

    public Vector2f getVector2f() {
        return new Vector2f(x, y);
    }
}
