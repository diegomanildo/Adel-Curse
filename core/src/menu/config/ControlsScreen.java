package menu.config;

import com.badlogic.gdx.Input;
import gameUtilities.Control;
import gameUtilities.Controls;
import utilities.Screen;
import utilities.*;

public final class ControlsScreen extends BasicOptionsScreen {
    private Options keys;
    private Texts actions;

    public ControlsScreen() {
        super();
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
    }

    private void setButton(Control c) {
        Render.setScreen(new PressAKeyScreen(c));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        keys.update();
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        actions.centerY();
        actions.setX(10f);

        keys.center();
        keys.setX(actions.getX() + actions.getWidth());
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
