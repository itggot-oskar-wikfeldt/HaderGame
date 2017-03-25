package me.hsogge.hadergame.level;

import se.wiklund.haderengine.graphics.Renderer;
import se.wiklund.haderengine.graphics.Texture;

/**
 * Created by bolle on 2017-03-24.
 */
public class Level {

    Ball ball;
    Texture texture = new Texture(0xFF000000);
    double[] function = new double[1920];
    double[] gradient = new double[1920];

    static int functionID = 0;

    public Level() {

        ball = new Ball(this, 150, 500);

        if (functionID == 0) {
            for (int i = 0; i < function.length; i++) {
                function[i] = 100 * Math.sin((float) i / 100) + 150;
                gradient[i] = Math.cos((float) i / 100);
            }
        } else {
            for (int i = 0; i < function.length; i++) {
                function[i] = Math.pow(2, (double) -0.01 * i + 10);
                gradient[i] = -7.1 * Math.pow(Math.E, -0.007 * i);
            }
        }

    }

    public void update(double delta) {
        ball.update(delta);
    }

    public void render() {
        for (int i = 0; i < function.length; i++) {
            Renderer.render(texture, i, (float) function[i], 1, (float) -function[i]);
        }
        ball.render();
    }

    public double getHeight(int x) {
        return function[x];
    }

    public double getGradient(int x) {
        return gradient[x];
    }

    public static void setFunctionID(int functionID) {
        Level.functionID = functionID;
    }

}
