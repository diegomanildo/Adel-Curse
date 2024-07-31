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
                new TextButton("SOUND SETTINGS", () -> Render.setScreen(new SoundSettingsScreen())),
                new TextButton("VIDEO SETTINGS", () -> Render.setScreen(new VideoSettingsScreen()))
        );

        buttons.forEach(b -> {
            table.add(b).center().minWidth(100f).minHeight(25f).padBottom(10f);
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
