package me.hsogge.hadergame.level;

import expr.Expr;
import expr.Parser;
import expr.SyntaxException;
import expr.Variable;
import me.hsogge.hadergame.math.Vector4f;
import me.hsogge.hadergame.state.Game;
import me.hsogge.hadergame.state.Settings;
import org.lwjgl.glfw.GLFW;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.input.InputEnabledViews;
import se.wiklund.haderengine.maths.Transform;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Level extends View {

    private List<Ball> balls = new ArrayList<>();

    private List<Vector4f> points = new ArrayList<>();

    private Texture lineTexture = new Texture(0xdd0aafde);

    private final int SIZE;
    private final int LINE_THICKNESS = 5;
    private Game game;

    private boolean mouseIsDown;

    public Level(Game game, int size) {
        super(new Texture(0xffffffff), new Transform(0, 0, 0, 0));

        SIZE = size;

        this.game = game;

        balls.add(new Ball(this, 150, 1000, 16));

        for (Ball ball : balls)
            addSubview(ball);


        if (!Settings.Data.functions.isEmpty()) {
            for (Settings.Function function : Settings.Data.functions)
                placeFunction(function.getMin(), function.getMax(), function.getFunction());

            for (Vector4f point : points)
                addSubview(new View(lineTexture, new Transform(point.getX(), point.getY(), 1, (int) (-LINE_THICKNESS / point.getA()))));
        }

        addSubview(new View(new Texture(0x88000000), new Transform(-SIZE, 0, SIZE * 2, 1)));
        addSubview(new View(new Texture(0x88000000), new Transform(0, -SIZE, 1, SIZE * 2)));

        //getTransform().move(-SIZE, SIZE);

        InputEnabledViews.setEnabled(this);
    }

    public void placeFunction(int min, int max, String functionString) {
        Expr function = null;
        try {
            function = Parser.parse(functionString);
        } catch (SyntaxException e) {
            System.err.println(e.explain());
        }

        if (function == null)
            return;

        Variable x = Variable.make("x");

        List<Vector4f> tempPoints = new ArrayList<>();

        for (int i = min; i <= max; i++) {
            x.setValue(i);

            Vector4f point = new Vector4f(i, (float) function.value(), 0, -LINE_THICKNESS);

            if (i != min) {
                point.setZ((point.getY() - tempPoints.get((i - min) - 1).getY()) / (point.getX() - tempPoints.get((i - min) - 1).getX()));
                point.setA((float) Math.cos(Math.atan(point.getZ())));
            }

            tempPoints.add(point);

        }

        points.addAll(tempPoints);
    }

    @Override
    public void onMouseButtonDown(int button) {
        super.onMouseButtonDown(button);

        if (button == GLFW.GLFW_MOUSE_BUTTON_1)
            mouseIsDown = true;
    }

    @Override
    public void onMouseButtonUp(int button) {
        super.onMouseButtonUp(button);
        if (button == GLFW.GLFW_MOUSE_BUTTON_1)
            mouseIsDown = false;
    }

    public void update(float delta) {

        if (mouseIsDown) {
            for (Ball ball : balls) {
                ball.setPosition(Cursor.getTransform().getX() - game.getOffsetX(), Cursor.getTransform().getY() - game.getOffsetY());
                ball.stop();
            }
        }
        for (Ball ball : balls)
            ball.update(delta);
    }

    int[] getLimits() {
        return new int[]{-SIZE, SIZE};
    }

    public List<Vector4f> getPoints() {
        return points;
    }
}
