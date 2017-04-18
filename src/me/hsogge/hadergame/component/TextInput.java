package me.hsogge.hadergame.component;

import org.lwjgl.glfw.GLFW;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.UIFont;
import se.wiklund.haderengine.ui.UILabel;

public class TextInput extends UIButton {

    private UILabel label;
    TextInputListener listener;
    Texture selectedTexture = new Texture(0xFF00ccff);
    Texture deselectedTexture;

    public TextInput(String text, UIFont font, int fontSize, Texture background, Transform transform) {
        super("", font, fontSize, background, transform);

        deselectedTexture = background;

        label = new UILabel(text, font, fontSize, transform.getWidth() / 2, transform.getHeight() / 2, true);
        addSubview(label);

    }

    public void setListener(TextInputListener listener) {
        this.listener = listener;
    }

    private boolean selected;

    public void select() {
        selected = true;
        setTexture(selectedTexture, false);
    }

    public void deselect() {
        selected = false;
        setTexture(deselectedTexture, false);
    }

    private void insertLetter(String letter) {
        label.setText(new StringBuilder(label.getText()).insert(label.getText().length()-position, letter).toString());
    }

    private boolean shiftIsDown;
    private int position = 0;

    @Override
    public void onKeyDown(int key) {
        super.onKeyDown(key);

        if (key == GLFW.GLFW_KEY_LEFT_SHIFT) {
            shiftIsDown = true;
            return;
        }

        if (selected) {

            if (key == GLFW.GLFW_KEY_BACKSPACE && label.getText().length() > 0 && label.getText().length() - position > 0) {
                label.setText(new StringBuilder(label.getText()).deleteCharAt(label.getText().length()-position-1).toString());
                return;
            }

            if (key == GLFW.GLFW_KEY_ENTER) {
                listener.onEnter(this);
                deselect();
                return;
            }

            if (key == GLFW.GLFW_KEY_LEFT) {
                position += 1;
                if (getText().length() - position < 0)
                    position = getText().length();
                return;
            } else if (key == GLFW.GLFW_KEY_RIGHT) {
                position -= 1;
                if (position < 0)
                    position = 0;
                return;
            }

            String keyStr = GLFW.glfwGetKeyName(key, 0);

            if (key == GLFW.GLFW_KEY_SPACE) {
                insertLetter(" ");
                return;
            } else if (keyStr != null) {
                if (shiftIsDown) switch (key) {
                    case GLFW.GLFW_KEY_8:
                        insertLetter("(");
                        return;
                    case GLFW.GLFW_KEY_9:
                        insertLetter(")");
                        return;
                    case GLFW.GLFW_KEY_BACKSLASH:
                        insertLetter("*");
                        return;
                    case GLFW.GLFW_KEY_RIGHT_BRACKET:
                        insertLetter("^");
                        return;
                    default:
                        insertLetter(keyStr);
                        return;
                }
                else
                    insertLetter(keyStr.toLowerCase());

            }
        }
    }

    @Override
    public void onKeyUp(int key) {
        super.onKeyUp(key);
        if (key == GLFW.GLFW_KEY_LEFT_SHIFT)
            shiftIsDown = false;
    }

    public void update(float delta) {
        super.update(delta);
    }

    public String getText() {
        return label.getText();
    }

}
