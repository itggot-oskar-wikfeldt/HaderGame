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


    public Level() {

        ball = new Ball(this, 64, 500);

        for (int i = 0; i < function.length; i++) {
            function[i] = 100 * Math.sin((float) i / 100) + 150;
            //function[i] = -0.01 * (float) Math.pow(i, 2) + 1000;
        }

    }

    public void update(double delta) {
        ball.update(delta);
    }

    public void render() {

        ball.render();

        for (int i = 0; i < function.length; i++) {
            Renderer.render(texture, i, (float) function[i], 1, (float) -function[i]);
        }
    }

    public double getHeight(int x) {
        return function[x];
    }

}
