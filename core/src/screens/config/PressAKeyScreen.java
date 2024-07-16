package screens.config;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import gameUtilities.Control;
import gameUtilities.Controls;
import screens.Screen;
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
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        pressAnyKey.center();

        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            Render.setScreen(new ControlsScreen());
            return;
        }

        if (Render.io.isAnyKeyPressed()) {
            setControl();
        }

        Render.b.begin();
        pressAnyKey.draw();
        Render.b.end();
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
