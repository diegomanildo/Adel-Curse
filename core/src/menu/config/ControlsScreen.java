package menu.config;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.utilities.Control;
import game.utilities.Controls;
import menu.MainMenuScreen;
import utilities.Label;
import utilities.Render;
import utilities.Screen;
import utilities.TextButton;

public final class ControlsScreen extends BasicOptionsScreen {
    public ControlsScreen() {
        super();
        Table table = new Table();
        table.setFillParent(true);

        for (int i = 0; i < Controls.size(); i++) {
            Control c = Controls.at(i);
            Label controlLabel = new Label(c.getAction() + ": ");
            TextButton controlButton = new TextButton(Input.Keys.toString(c.getKey()), () -> Render.setScreen(new PressAKeyScreen(c)));

            table.add(controlLabel)
                    .left();

            table.add(controlButton)
                    .left()
                    .padLeft(10f)
                    .padBottom(10f);

            table.row();
        }

        stage.addActor(table);
    }

    @Override
    protected Screen getBackScreen() {
        return new MainMenuScreen();
    }

    @Override
    protected String getTitleScreen() {
        return "CONTROLS";
    }
}
