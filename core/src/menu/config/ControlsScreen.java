package menu.config;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.utilities.Control;
import game.utilities.Controls;
import utilities.Label;
import utilities.Render;
import utilities.Screen;
import utilities.TextButton;

public final class ControlsScreen extends BasicOptionsScreen {
    public ControlsScreen(Screen backScreen) {
        super(backScreen);
        Table table = new Table();
        table.setFillParent(true);
        table.left().padLeft(10f);

        for (int i = 0; i < Controls.size(); i++) {
            Control c = Controls.at(i);
            Label controlLabel = new Label(c.getAction() + ": ");
            TextButton controlButton = new TextButton(Input.Keys.toString(c.getKey()), () -> Render.setScreen(new PressAKeyScreen(backScreen, c)));

            table.add(controlLabel).left();
            table.add(controlButton).center().width(45f).height(45f).padBottom(10f);
            table.row();
        }

        stage.addActor(table);
    }

    @Override
    protected String getTitleScreen() {
        return "CONTROLS";
    }
}
