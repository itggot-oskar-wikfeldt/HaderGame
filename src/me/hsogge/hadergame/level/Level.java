package me.hsogge.hadergame.level;

import me.hsogge.hadergame.math.Vector2f;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.graphics.Renderer;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.input.Mouse;
import se.wiklund.haderengine.input.MouseButtonListener;

import java.util.Vector;

/**
 * Created by bolle on 2017-03-24.
 */
public class Level {

    //Ball ball;
    Ball2 ball2;
    Texture texture = new Texture(0xFF000000);
    double[] function = new double[1920];
    double[] gradient = new double[1920];

    MouseButtonListener mouseButtonListener;

    Engine engine;

    boolean mouseIsDown;

    static int functionID = 0;

    public Level(Engine engine) {



        this.engine = engine;

        //ball = new Ball(this, 70, 1000);
        ball2 = new Ball2(this, 150, 1000);

        for (int i = 0; i < function.length / 2; i++) {
            function[i] = Math.pow(2, (double) -0.01 * i + 10) + 150;
            gradient[i] = -7.1 * Math.pow(Math.E, -0.007 * i);
        }

        for (int i = function.length / 2; i < function.length; i++) {
            function[i] = 150 * Math.cos((float) i / 100) + 200;
            gradient[i] = -1.5 * Math.sin((float) i / 100);
        }

        mouseButtonListener = new MouseButtonListener() {
            @Override
            public void onMouseButtonDown(int i) {
                mouseIsDown = true;

            }

            @Override
            public void onMouseButtonUp(int i) {

                mouseIsDown = false;

            }
        };

        Mouse.addMouseButtonListener(mouseButtonListener);

    }

    public void update(double delta) {

        if (mouseIsDown) {
            //ball.setPos(Cursor.getTransform().getX(), Cursor.getTransform().getY());
            //ball.stop();
            ball2.setPosition(Cursor.getTransform().getX(), Cursor.getTransform().getY());
            ball2.stop();
        }


        //ball.update(delta);
        ball2.update(delta);
    }

    public void render() {
        for (int i = 0; i < function.length; i++) {
            Renderer.render(texture, i, (float) function[i], 1, (float) -function[i]);
        }
        //ball.render();
        ball2.render();
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
