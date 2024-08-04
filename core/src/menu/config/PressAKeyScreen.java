package menu.config;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.utilities.Control;
import game.utilities.Controls;
import utilities.Label;
import utilities.Render;
import utilities.Screen;

public final class PressAKeyScreen extends Screen {
    private final Label pressAnyKey;
    private final Screen backControlsScreen;
    private final Control control;

    public PressAKeyScreen(Screen backControlsScreen, Control control) {
        super();
        this.backControlsScreen = backControlsScreen;
        this.control = control;

        Table table = new Table();
        table.setFillParent(true);
        pressAnyKey = new Label("Press a key for " + control.getAction() + "...");
        table.add(pressAnyKey).center();
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (stage.isKeyPressed(Input.Keys.ESCAPE)) {
            Render.setScreen(new ControlsScreen(backControlsScreen));
            return;
        }

        if (stage.isAnyKeyPressed()) {
            setControl();
        }
    }

    private void setControl() {
        int keyPressed = stage.getKeyPressed();

        if (Controls.exists(keyPressed) && control.getKey() != keyPressed) {
            pressAnyKey.setColor(Color.RED);
            pressAnyKey.setText("Key \"" + Input.Keys.toString(keyPressed) + "\" is already assigned\nPress another key for " + control.getAction() + "...");;
        } else {
            pressAnyKey.setColor(Color.WHITE);
            Controls.set(control.getAction(), keyPressed);
            Render.setScreen(new ControlsScreen(backControlsScreen));
        }
    }
}
