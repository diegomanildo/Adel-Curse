package menu.config;

import com.badlogic.gdx.Input;
import game.utilities.Control;
import game.utilities.Controls;
import utilities.Screen;
import utilities.Fonts;
import utilities.Render;
import utilities.Text;

public final class PressAKeyScreen extends Screen {
    private final Text pressAnyKey;
    private final Control control;

    public PressAKeyScreen(Control control) {
        super();
        this.control = control;
        pressAnyKey = new Text(Fonts.DEFAULT2, "Press a key for " + control.getAction() + "...");
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            Render.setScreen(new ControlsScreen());
            return;
        }

        if (Render.io.isAnyKeyPressed()) {
            setControl();
        }
    }

    private void setControl() {
        int keyPressed = Render.io.getKeyPressed();

        if (Controls.exists(keyPressed)) {
            pressAnyKey.setText("Key \"" + Input.Keys.toString(keyPressed) + "\" is already assigned\nPress another key for " + control.getAction() + "...");
        } else {
            Controls.set(control.getAction(), keyPressed);
            Render.setScreen(new ControlsScreen());
        }
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        pressAnyKey.center();
    }
}
