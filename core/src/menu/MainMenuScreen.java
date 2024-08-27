package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import game.GameScreen;
import menu.config.ControlsScreen;
import menu.config.SettingsScreen;
import utilities.Render;
import utilities.TextButton;

public final class MainMenuScreen extends BasicMainMenuScreen {
    public MainMenuScreen() {
        super();
        Table table = new Table();
        table.setFillParent(true);
        Array<TextButton> buttons = new Array<>();
        buttons.addAll(
                new TextButton(Render.currentLanguage.playBtn(), () -> {
                    backgroundSong.fadeOut(FADE_TIME);
                    Render.setScreen(new GameScreen());
                }),
                new TextButton(Render.currentLanguage.controlsBtn(), () -> Render.setScreen(new ControlsScreen(new MainMenuScreen()))),
                new TextButton(Render.currentLanguage.settingsBtn(), () -> Render.setScreen(new SettingsScreen(new MainMenuScreen()))),
                new TextButton(Render.currentLanguage.quitBtn(), () -> Gdx.app.exit())
        );

        buttons.forEach(b -> {
            table.add(b).center().width(200f).height(45f).padBottom(10f);
            table.row();
        });

        stage.addActor(table);
    }
}
