package me.hsogge.hadergame.component;

import me.hsogge.hadergame.Style;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.glfw.GLFW;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.input.Keyboard;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.UIFont;
import se.wiklund.haderengine.ui.UILabel;

public class TextInput extends UIButton {

    private UILabel label;
    TextInputListener listener;
    Texture selectedTexture = new Texture(0xFF00ccff);
    Texture deselectedTexture;

    public TextInput(String text, UIFont font, float fontSize, Texture background, float x, float y, int width, int height) {
        super("", font, fontSize, background, x, y, width, height);

        deselectedTexture = background;

        label = new UILabel(text, font, fontSize, x + width/2, y+height/2, true);

    }

    public void setListener(TextInputListener listener) {
        this.listener = listener;
    }

    private boolean selected;

    public void select() {
        selected = true;
        setTexture(selectedTexture, true);
    }

    public void deselect() {
        selected = false;
        setTexture(deselectedTexture, true);
    }

    public void update(double delta) {
        super.update(delta);

        if (selected) {

            if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_BACKSPACE) && label.getText().length() > 0)
                label.setText(label.getText().substring(0,label.getText().length()-1));

            for (int i = 0; i < 100; i++) {
                if (Keyboard.isKeyPressed(i)) {
                    String key = GLFW.glfwGetKeyName(i, 0);

                    System.out.println(i);

                    if (i == GLFW.GLFW_KEY_SPACE)
                        label.setText(label.getText() + " ");
                    else if (key != null) {
                        if (Keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) switch (i) {
                            case GLFW.GLFW_KEY_8:
                                label.setText(label.getText() + "(");
                                break;
                            case GLFW.GLFW_KEY_9:
                                label.setText(label.getText() + ")");
                                break;
                            case GLFW.GLFW_KEY_BACKSLASH:
                                label.setText(label.getText() + "*");
                                break;
                            default:
                                label.setText(label.getText() + key);
                                break;
                        }
                        else
                            label.setText(label.getText() + key.toLowerCase());
                    }
                }
            }

            if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_ENTER)) {
                listener.onEnter(this);
                deselect();
            }
        }
    }

    public void render() {
        super.render();
        label.render();
    }

    public String getText() {
        return label.getText();
    }

}
