package me.hsogge.hadergame.state;

import me.hsogge.hadergame.Style;
import me.hsogge.hadergame.level.Level;
import me.hsogge.hadergame.math.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.input.InputEnabledViews;
import se.wiklund.haderengine.ui.UILabel;

import java.util.ArrayList;
import java.util.List;

public class Game extends State {

    private Engine engine;
    private Level level;
    private UILabel controls;

    private List<Vector2f> mousePositions = new ArrayList<>();

    public Game(Engine engine, Level level) {

        InputEnabledViews.disableAll();

        this.engine = engine;
        this.level = level;

        level.setGame(this);
        addSubview(level);

        controls = new UILabel("controls: place ball: mouse1      pan: arrows, wasd or mouse3       follow ball: f      enlarge ball: +     shrink ball: -      shape-shift: *      reset ball: r", Style.FONT_COORD, 32, 0, engine.HEIGHT - 32, false);
        addSubview(controls);

        for(int i = 0; i < 60; i++)
            mousePositions.add(new Vector2f(0,0));

    }

    private boolean mouse3IsDown;
    private boolean mouse1IsDown;

    @Override
    public void onMouseButtonDown(int button) {
        super.onMouseButtonDown(button);
        level.onMouseButtonDown(button);

        if (button == GLFW.GLFW_MOUSE_BUTTON_3)
            mouse3IsDown = true;

        if (button == GLFW.GLFW_MOUSE_BUTTON_1)
            mouse1IsDown = true;

        lastMouseX = Cursor.getTransform().getX();
        lastMouseY = Cursor.getTransform().getY();
    }

    @Override
    public void onMouseButtonUp(int button) {
        super.onMouseButtonUp(button);
        level.onMouseButtonUp(button);

        if (button == GLFW.GLFW_MOUSE_BUTTON_3)
            mouse3IsDown = false;
        if (button == GLFW.GLFW_MOUSE_BUTTON_1)
            mouse1IsDown = false;

    }

    private boolean rightIsDown;
    private boolean leftIsDown;
    private boolean upIsDown;
    private boolean downIsDown;
    private boolean fIsDown;
    private boolean addIsDown;
    private boolean subtractIsDown;
    private boolean starIsPressed;
    private boolean starIsDown;
    private boolean rIsDown;

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
        else if (key == GLFW.GLFW_KEY_KP_ADD)
            addIsDown = true;
        else if (key == GLFW.GLFW_KEY_KP_SUBTRACT)
            subtractIsDown = true;
        else if (key == GLFW.GLFW_KEY_KP_MULTIPLY)
            starIsDown = true;
        else if (key == GLFW.GLFW_KEY_R)
            rIsDown = true;


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
        else if (key == GLFW.GLFW_KEY_KP_ADD)
            addIsDown = false;
        else if (key == GLFW.GLFW_KEY_KP_SUBTRACT)
            subtractIsDown = false;
        else if (key == GLFW.GLFW_KEY_KP_MULTIPLY)
            starIsDown = false;
        else if (key == GLFW.GLFW_KEY_R)
            rIsDown = false;

    }

    private float vel = 15f;
    private float offsetX = 256;
    private float offsetY = 256;

    private float lastMouseX;
    private float lastMouseY;
    private boolean lastStarDown;

    @Override
    public void update(float delta) {
        GL11.glClearColor(1, 1, 1, 1);
        level.update(delta);

        if (rIsDown)
            level.resetBall();

        if (fIsDown) {
            offsetX = -level.getBalls().get(0).getTransform().getX() - level.getBalls().get(0).getRadius() + Engine.WIDTH / 2;
            offsetY = -level.getBalls().get(0).getTransform().getY() - level.getBalls().get(0).getRadius() + Engine.HEIGHT / 2;
        } else {

            if (rightIsDown)
                offsetX -= vel;

            if (leftIsDown)
                offsetX += vel;

            if (upIsDown)
                offsetY -= vel;

            if (downIsDown)
                offsetY += vel;

            if (mouse1IsDown)
                level.moveBall(Cursor.getTransform().getX(), Cursor.getTransform().getY());

        }

        if (mouse3IsDown) {

            float currentMouseX = Cursor.getTransform().getX();
            float currentMouseY = Cursor.getTransform().getY();

            offsetX += currentMouseX - lastMouseX;
            offsetY += currentMouseY - lastMouseY;

            lastMouseX = currentMouseX;
            lastMouseY = currentMouseY;

        }

        if (addIsDown) {
            level.enlargeBall();
        }
        if (subtractIsDown) {
            level.shrinkBall();
        }

        if (starIsDown && !lastStarDown)
            starIsPressed = true;
        else
            starIsPressed = false;

        lastStarDown = starIsDown;

        if (starIsPressed)
            level.shapeShift();

        level.getTransform().setPosition(offsetX, offsetY);
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

}
