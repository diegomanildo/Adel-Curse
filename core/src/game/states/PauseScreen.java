package game.states;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import menu.MainMenuScreen;
import menu.config.SettingsScreen;
import utilities.Render;
import utilities.SubScreen;
import utilities.TextButton;

public class PauseScreen extends SubScreen {
    public PauseScreen() {
        super(true);
        setShow(false);

        Table table = new Table();
        table.setFillParent(true);

        Array<TextButton> buttons = new Array<>();

        buttons.addAll(
                new TextButton("Resume", () -> setShow(false)),
                new TextButton("Settings", () -> Render.setScreen(new SettingsScreen())),
                new TextButton("Back to menu", () -> Render.setScreen(new MainMenuScreen()))
        );

        buttons.forEach(b -> table.add(b).fillX().uniformX().pad(10f).row());

        stage.addActor(table);
    }
}
