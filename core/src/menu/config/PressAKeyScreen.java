package menu.config;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import gameUtilities.Control;
import gameUtilities.Controls;
import menu.Screen;
import utilities.Fonts;
import utilities.Render;
import utilities.Text;

public final class PressAKeyScreen extends Screen {
    private Text pressAnyKey;
    private final Control control;

    public PressAKeyScreen(Control control) {
        super();
        this.control = control;
    }

    @Override
    public void show() {
        super.show();
        pressAnyKey = new Text(Fonts.DEFAULT2, "Press a key for " + control.getAction() + "...");
        pressAnyKey.center();
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
            pressAnyKey.font.setColor(Color.RED);
        } else {
            Controls.set(control.getAction(), keyPressed);
            Render.setScreen(new ControlsScreen());
        }
    }
}
