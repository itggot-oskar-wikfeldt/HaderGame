package me.hsogge.hadergame.level;

import bsh.EvalError;
import bsh.Interpreter;
import com.sun.deploy.util.ArrayUtil;
import me.hsogge.hadergame.math.Vector2f;
import me.hsogge.hadergame.state.Settings;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.graphics.Renderer;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.input.Mouse;
import se.wiklund.haderengine.input.MouseButtonListener;
import se.wiklund.haderengine.ui.EnabledUIComponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.*;

public class Level {

    private Ball ball;
    private Texture texture = new Texture(0xFF0aafde);
    private double[] function = new double[1920];
    private double[] gradient = new double[1920];
    private double[] texHeight = new double[1920];
    private Engine engine;
    private boolean mouseIsDown;

    public Level(Engine engine) {
        EnabledUIComponents.disableAll();

        this.engine = engine;
        ball = new Ball(this, 150, 1000, 32);

        //placeFunction(0, function.length / 2, "pow(2, -0.01 * x + 10) + 230", "-7.1 * pow(e, -0.007 * x)");
        //placeFunction(function.length / 2, function.length, "200 * -cos(x / 150) + 230 + 200", "2 * sin(x / 150) / 1.5");

        placeFunction(0, function.length / 2, Settings.function, Settings.gradient);
        placeFunction(function.length / 2, function.length, "200 * -cos(x / 150) + 230 + 200", "2 * sin(x / 150) / 1.5");

        MouseButtonListener mouseButtonListener = new MouseButtonListener() {
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

    public void placeFunction(int min, int max, String functionString, String gradientString) {
        Interpreter interpreter = new Interpreter();
        functionString = functionString.replaceAll("pow", "Math.pow").replaceAll("sin", "Math.sin")
                .replaceAll("cos", "Math.cos").replaceAll("tan", "Math.tan").replaceAll("e", "Math.E")
                .replaceAll("log", "Math.log");
        gradientString = gradientString.replaceAll("pow", "Math.pow").replaceAll("sin", "Math.sin")
                .replaceAll("cos", "Math.cos").replaceAll("tan", "Math.tan").replaceAll("e", "Math.E")
                .replaceAll("log", "Math.log");

        for (int i = min; i < max; i++) {

            double functionResult = 0;
            double gradientResult = 0;
            String localFunction = functionString.replaceAll("x", "(float) " + Integer.toString(i));
            String localGradient = gradientString.replaceAll("x", "(float) " + Integer.toString(i));
            try {
                interpreter.eval("result = " + localFunction);
                functionResult = (double) interpreter.get("result");
                interpreter.eval("result = " + localGradient);
                gradientResult = (double) interpreter.get("result");
            } catch (EvalError evalError) {
                evalError.printStackTrace();
            }
            function[i] = functionResult;
            gradient[i] = gradientResult;
            texHeight[i] = Math.cos(Math.atan(gradient[i]));
        }
    }

    public void update(double delta) {

        if (mouseIsDown) {
            ball.setPosition(Cursor.getTransform().getX(), Cursor.getTransform().getY());
            ball.stop();
        }

        ball.update(delta);
    }

    public void render() {
        for (int i = 0; i < function.length; i++) {
            Renderer.render(texture, i, (float) function[i], 1, (float) (-5 / texHeight[i]));
        }

        ball.render();
    }

    int[] getLimits() {
        return new int[]{0, function.length};
    }

    double getHeight(int x) {
        return function[x];
    }

    double getGradient(int x) {
        return gradient[x];
    }

}
