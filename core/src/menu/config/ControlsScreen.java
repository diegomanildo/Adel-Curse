package menu.config;

import com.badlogic.gdx.Input;
import gameUtilities.Control;
import gameUtilities.Controls;
import menu.Screen;
import utilities.*;

public final class ControlsScreen extends BasicOptionsScreen {
    private Options keys;
    private Texts actions;

    @Override
    public void show() {
        super.show();
        Button[] buttons = new Button[Controls.size()];
        Text[] texts = new Text[buttons.length];

        for (int i = 0; i < buttons.length; i++) {
            Control c = Controls.at(i);
            buttons[i] = new Button(Fonts.DEFAULT2, Input.Keys.toString(c.getKey()), () -> setButton(c));
            buttons[i].showBackground(false);
            texts[i] = new Text(Fonts.DEFAULT2, c.getAction() + ": ");
        }

        keys = new Options(buttons);
        actions = new Texts(texts);

        actions.centerY();
        actions.setX(10f);

        keys.center();
        keys.setX(actions.getX() + actions.getWidth());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        keys.update();
    }

    private void setButton(Control c) {
        Render.setScreen(new PressAKeyScreen(c));
    }

    @Override
    protected Screen getBackScreen() {
        return new OptionsScreen();
    }

    @Override
    protected String getTitleScreen() {
        return "CONTROLS";
    }
}
