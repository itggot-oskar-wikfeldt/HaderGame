package me.hsogge.hadergame.state;

import me.hsogge.hadergame.component.TextInput;
import me.hsogge.hadergame.component.TextInputListener;
import org.lwjgl.opengl.GL11;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.input.InputEnabledViews;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.listener.UIButtonListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static me.hsogge.hadergame.Style.*;

public class Settings extends State {

    private Engine engine;
    private UIButton buttonBack;
    private UIButton buttonAdd;
    private UIButtonListener buttonListener;
    private boolean buttonUp = false;
    private UIButton pressedButton;
    private UIButton buttonAddStandard;
    private List<FunctionInput> functionInputs = new CopyOnWriteArrayList<>();

    public Settings(Engine engine) {

        InputEnabledViews.disableAll();

        GL11.glClearColor(1, 1, 1, 1);

        this.engine = engine;


        buttonAdd = new UIButton("Add Function", FONT, 48, COLOR_GOOD, new Transform(BTN_MARGIN, Engine.HEIGHT - (BTN_HEIGHT + BTN_MARGIN), 256, BTN_HEIGHT));
        buttonAddStandard = new UIButton("Add ex. Functions", FONT, 40, COLOR_GOOD, new Transform(BTN_MARGIN, Engine.HEIGHT - 2 * (BTN_HEIGHT + BTN_MARGIN), 256, BTN_HEIGHT));
        buttonBack = new UIButton("Back", FONT, 48, COLOR_BAD, new Transform(BTN_MARGIN, Engine.HEIGHT - 3 * (BTN_HEIGHT + BTN_MARGIN), 256, BTN_HEIGHT));

        buttonListener = new UIButtonListener() {
            @Override
            public void onButtonDown(UIButton uiButton, int i) {

            }

            @Override
            public void onButtonUp(UIButton uiButton, int i) {
                buttonUp = true;
                pressedButton = uiButton;
            }
        };

        buttonBack.addButtonListener(buttonListener);
        buttonAdd.addButtonListener(buttonListener);
        buttonAddStandard.addButtonListener(buttonListener);

        addSubview(buttonBack);
        addSubview(buttonAdd);
        addSubview(buttonAddStandard);

        for (Function function : Data.functions) {

            functionInputs.add(new FunctionInput(new String[]{function.getFunction(), Integer.toString(function.getMin()), Integer.toString(function.getMax())}, functionInputs.size()));
            functionInputs.get(functionInputs.size() - 1).setFunction(function);
            addSubview(functionInputs.get(functionInputs.size() - 1));
        }

        addSubview(LBL_POWEREDBY);

    }

    @Override
    public void update(float delta) {

        buttonBack.update(delta);
        buttonAdd.update(delta);
        buttonAddStandard.update(delta);

        for (FunctionInput functionInput : functionInputs) {
            if (functionInput.shouldDelete) {
                Data.functions.remove(functionInput.function);
                functionInput.setHidden(true);
                functionInputs.remove(functionInput);
            }
        }

        for (FunctionInput funcInput : functionInputs)
            funcInput.update(delta);

        if (buttonUp) {
            buttonUp = false;
            for (FunctionInput functionInput : functionInputs)
                for (TextInput textInput : functionInput.getTextInputs())
                    textInput.deselect();


            if (pressedButton == buttonBack) {
                for (FunctionInput functionInput : functionInputs)
                    if (!Data.functions.contains(functionInput.function))
                        Data.functions.add(functionInput.function);
                engine.setState(new Menu(engine));
            } else if (pressedButton == buttonAddStandard) {
                addExampleFunctions();
            } else if (pressedButton == buttonAdd) {
                functionInputs.add(new FunctionInput(functionInputs.size()));
                addSubview(functionInputs.get(functionInputs.size() - 1));
            } else {
                for (FunctionInput functionInput : functionInputs)
                    for (TextInput textInput : functionInput.getTextInputs())
                        if (pressedButton == textInput)
                            textInput.select();
            }
        }
    }

    private void addExampleFunctions() {
        functionInputs.add(new FunctionInput(new String[]{"750 * 0.99^x + 231", "0", "960"}, functionInputs.size()));
        functionInputs.add(new FunctionInput(new String[]{"200 * -cos(x / 150) + 430", "960", "1920"}, functionInputs.size()));
        addSubview(functionInputs.get(functionInputs.size() - 1));
        addSubview(functionInputs.get(functionInputs.size() - 2));
    }

    public class FunctionInput extends View {

        private TextInput funcInput;
        private TextInput minInput;
        private TextInput maxInput;
        private UIButton btnDelete;
        private Function function;
        private TextInputListener textInputListener;
        private List<TextInput> textInputs = new ArrayList<>();

        private FunctionInput(String[] premadeVars, int index) {
            super(new Texture(0x00000000), new Transform(2 * BTN_MARGIN + 256, Engine.HEIGHT - (index + 1) * (BTN_MARGIN + BTN_HEIGHT), Engine.WIDTH - (2 * BTN_MARGIN + 256) - BTN_MARGIN, BTN_HEIGHT));

            textInputListener = textInput -> {
                function.setFunction(funcInput.getText());
                int minInputInt;
                int maxInputInt;

                try {
                    minInputInt = Integer.parseInt(minInput.getText());
                } catch (NumberFormatException e) {
                    minInputInt = 0;
                }

                try {
                    maxInputInt = Integer.parseInt(maxInput.getText());
                } catch (NumberFormatException e) {
                    maxInputInt = 0;
                }

                function.setMin(minInputInt);
                function.setMax(maxInputInt);
            };

            funcInput = new TextInput(premadeVars[0], FONT, 48, COLOR_NORMAL, new Transform(256 + BTN_MARGIN, 0, getTransform().getWidth() - 3 * (BTN_MARGIN + 256), BTN_HEIGHT));
            funcInput.addButtonListener(buttonListener);
            funcInput.setListener(textInputListener);
            textInputs.add(funcInput);

            minInput = new TextInput(premadeVars[1], FONT, 48, COLOR_NORMAL, new Transform(getTransform().getWidth() - 2 * 256 - BTN_MARGIN, 0, 256, BTN_HEIGHT));
            minInput.addButtonListener(buttonListener);
            minInput.setListener(textInputListener);
            textInputs.add(minInput);

            maxInput = new TextInput(premadeVars[2], FONT, 48, COLOR_NORMAL, new Transform(getTransform().getWidth() - 256, 0, 256, BTN_HEIGHT));
            maxInput.addButtonListener(buttonListener);
            maxInput.setListener(textInputListener);
            textInputs.add(maxInput);

            addSubview(funcInput);
            addSubview(minInput);
            addSubview(maxInput);

            btnDelete = new UIButton("Delete", FONT, 48, COLOR_BAD, new Transform(0, 0, 256, BTN_HEIGHT));
            btnDelete.addButtonListener(new UIButtonListener() {
                @Override
                public void onButtonDown(UIButton uiButton, int i) {

                }

                @Override
                public void onButtonUp(UIButton uiButton, int i) {
                    delete();
                }
            });

            addSubview(btnDelete);

            function = new Function(funcInput.getText(), Integer.parseInt(minInput.getText()), Integer.parseInt(maxInput.getText()));

        }

        private FunctionInput(int index) {
            this(new String[]{"", "0", "0"}, index);
        }

        public void update(float delta) {
            funcInput.update(delta);
            minInput.update(delta);
            maxInput.update(delta);

            btnDelete.update(delta);
        }

        public boolean shouldDelete = false;

        public void delete() {
            shouldDelete = true;
        }

        public void setFunction(Function function) {
            this.function = function;
        }

        public Function getFunction() {
            return function;
        }

        public List<TextInput> getTextInputs() {
            return textInputs;
        }

    }

    public static class Data {
        public static List<Function> functions = new ArrayList<>();
    }

    public class Function {

        private String function;
        private int min, max;

        private Function(String function, int min, int max) {
            this.function = function;
            this.min = min;
            this.max = max;
        }

        public String getFunction() {
            return function;
        }

        public void setFunction(String function) {
            this.function = function;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }
    }
}
