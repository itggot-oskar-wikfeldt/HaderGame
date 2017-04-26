package me.hsogge.hadergame.state;

import me.hsogge.hadergame.level.Level;
import me.hsogge.hadergame.math.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.input.InputEnabledViews;

public class Game extends State {

    Engine engine;

    Level level;

    public Game(Engine engine) {
        InputEnabledViews.disableAll();

        this.engine = engine;

        level = new Level(this, 19200);

        addSubview(level);

    }

    boolean mouse3IsDown;

    @Override
    public void onMouseButtonDown(int button) {
        super.onMouseButtonDown(button);
        level.onMouseButtonDown(button);

        if (button == GLFW.GLFW_MOUSE_BUTTON_3) {
            mouse3IsDown = true;
            lastMouseX = Cursor.getTransform().getX();
            lastMouseY = Cursor.getTransform().getY();
        }
    }

    @Override
    public void onMouseButtonUp(int button) {
        super.onMouseButtonUp(button);
        level.onMouseButtonUp(button);

        if (button == GLFW.GLFW_MOUSE_BUTTON_3)
            mouse3IsDown = false;

    }

    boolean rightIsDown;
    boolean leftIsDown;
    boolean upIsDown;
    boolean downIsDown;

    @Override
    public void onKeyDown(int key) {
        super.onKeyDown(key);
        if (key == GLFW.GLFW_KEY_ESCAPE)
            engine.setState(new Menu(engine));
        else if (key == GLFW.GLFW_KEY_RIGHT || key == GLFW.GLFW_KEY_D)
            rightIsDown = true;
        else if (key == GLFW.GLFW_KEY_LEFT || key == GLFW.GLFW_KEY_A)
            leftIsDown = true;
        else if (key == GLFW.GLFW_KEY_UP || key == GLFW.GLFW_KEY_W)
            upIsDown = true;
        else if (key == GLFW.GLFW_KEY_DOWN || key == GLFW.GLFW_KEY_S)
            downIsDown = true;
    }

    @Override
    public void onKeyUp(int key) {
        super.onKeyUp(key);

        if (key == GLFW.GLFW_KEY_RIGHT || key == GLFW.GLFW_KEY_D)
            rightIsDown = false;
        else if (key == GLFW.GLFW_KEY_LEFT || key == GLFW.GLFW_KEY_A)
            leftIsDown = false;
        else if (key == GLFW.GLFW_KEY_UP || key == GLFW.GLFW_KEY_W)
            upIsDown = false;
        else if (key == GLFW.GLFW_KEY_DOWN || key == GLFW.GLFW_KEY_S)
            downIsDown = false;

    }

    private float vel = 10f;
    private float offsetX = 256;
    private float offsetY = 256;

    private float lastMouseX;
    private float lastMouseY;

    @Override
    public void update(float delta) {

        if (rightIsDown)
            offsetX -= vel;
        if (leftIsDown)
            offsetX += vel;
        if (upIsDown)
            offsetY -= vel;
        if (downIsDown)
            offsetY += vel;

        if (mouse3IsDown) {

            float currentMouseX = Cursor.getTransform().getX();
            float currentMouseY = Cursor.getTransform().getY();

            offsetX += currentMouseX - lastMouseX;
            offsetY += currentMouseY - lastMouseY;

            lastMouseX = currentMouseX;
            lastMouseY = currentMouseY;

        }

        level.getTransform().setPosition(offsetX, offsetY);
        level.update(delta);
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }
}
