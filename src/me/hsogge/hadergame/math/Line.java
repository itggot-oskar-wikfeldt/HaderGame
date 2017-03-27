package me.hsogge.hadergame.math;

public class Line {

    private float x1, y1, x2, y2;

    public Line(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void move(float dx, float dy) {
        x1 += dx;
        x2 += dx;
        y1 += dy;
        y2 += dy;
    }

}
