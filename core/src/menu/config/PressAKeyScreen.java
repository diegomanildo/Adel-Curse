package menu.config;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import game.utilities.Control;
import game.utilities.Controls;
import utilities.Render;
import utilities.Screen;

public final class PressAKeyScreen extends Screen {
    private final Label pressAnyKey;
    private final Control control;

    public PressAKeyScreen(Control control) {
        super();
        this.control = control;
        pressAnyKey = new Label("Press a key for " + control.getAction() + "...", Render.skin);

        stage.addActor(pressAnyKey);
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

        if (Controls.exists(keyPressed) && control.getKey() != keyPressed) {
            pressAnyKey.setColor(Color.RED);
            pressAnyKey.setText("Key \"" + Input.Keys.toString(keyPressed) + "\" is already assigned\nPress another key for " + control.getAction() + "...");;
        } else {
            pressAnyKey.setColor(Color.WHITE);
            Controls.set(control.getAction(), keyPressed);
            Render.setScreen(new ControlsScreen());
        }
    }
}
