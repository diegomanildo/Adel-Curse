package menu.config;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Screen;
import utilities.TextButton;

public final class OptionsScreen extends BasicOptionsScreen {
    public OptionsScreen() {
        super();

        Table table = new Table();
        table.setFillParent(true);
        Array<TextButton> buttons = new Array<>();
        buttons.addAll(
                new TextButton("CONTROLS", () -> Render.setScreen(new ControlsScreen())),
                new TextButton("SETTINGS", () -> Render.setScreen(new SettingsScreen()))
        );

        buttons.forEach(b -> {
            table.add(b).center().width(200f).height(45f).padBottom(10f);
            table.row();
        });

        stage.addActor(table);
    }

    @Override
    protected Screen getBackScreen() {
        return new MainMenuScreen();
    }

    @Override
    protected String getTitleScreen() {
        return "OPTIONS";
    }
}
