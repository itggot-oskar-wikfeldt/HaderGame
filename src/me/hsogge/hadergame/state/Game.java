package me.hsogge.hadergame.state;

import me.hsogge.hadergame.level.Level;
import org.lwjgl.glfw.GLFW;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.input.InputEnabledViews;

public class Game extends State {

    private Engine engine;
    private Level level;

    public Game(Engine engine, Level level) {

        InputEnabledViews.disableAll();

        this.engine = engine;
        this.level = level;

        level.setGame(this);

        addSubview(level);

    }

    private boolean mouse3IsDown;

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

    private boolean rightIsDown;
    private boolean leftIsDown;
    private boolean upIsDown;
    private boolean downIsDown;
    private boolean fIsDown;

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
        else if (key == GLFW.GLFW_KEY_F)
            fIsDown = true;

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
        else if (key == GLFW.GLFW_KEY_F)
            fIsDown = false;

    }

    private float vel = 15f;
    private float offsetX = 256;
    private float offsetY = 256;

    private float lastMouseX;
    private float lastMouseY;

    @Override
    public void update(float delta) {
        level.update(delta);

        if (fIsDown) {
            offsetX = -level.getBalls().get(0).getTransform().getX() + Engine.WIDTH / 2;
            offsetY = -level.getBalls().get(0).getTransform().getY() + Engine.HEIGHT / 2;
        } else {

            if (rightIsDown) {
                offsetX -= vel;
            }
            if (leftIsDown) {
                offsetX += vel;
            }
            if (upIsDown) {
                offsetY -= vel;
            }
            if (downIsDown) {
                offsetY += vel;
            }
        }

        if (mouse3IsDown) {

            float currentMouseX = Cursor.getTransform().getX();
            float currentMouseY = Cursor.getTransform().getY();

            offsetX += currentMouseX - lastMouseX;
            offsetY += currentMouseY - lastMouseY;

            lastMouseX = currentMouseX;
            lastMouseY = currentMouseY;

        }

        level.getTransform().setPosition(offsetX, offsetY);
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

}
