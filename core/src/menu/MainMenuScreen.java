package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import game.GameScreen;
import menu.config.OptionsScreen;
import utilities.Render;
import utilities.TextButton;

public final class MainMenuScreen extends BasicMainMenuScreen {
    public MainMenuScreen() {
        super();
        Table table = new Table();
        table.setFillParent(true);
        Array<TextButton> buttons = new Array<>();
        TextButton playButton = new TextButton("PLAY", () -> {
            backgroundSong.fadeOut(FADE_TIME);
            Render.setScreen(new GameScreen());
        });
        TextButton optionsButton = new TextButton("OPTIONS", () -> Render.setScreen(new OptionsScreen()));
        TextButton quitButton = new TextButton("QUIT", () -> Gdx.app.exit());

        buttons.addAll(
                playButton,
                optionsButton,
                quitButton
        );

        buttons.forEach(b -> {
            table.add(b).center().minWidth(100f).minHeight(25f).padBottom(10f);
            table.row();
        });

        stage.addActor(table);
    }
}
