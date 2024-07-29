package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import game.GameScreen;
import menu.config.OptionsScreen;
import utilities.Render;

public final class MainMenuScreen extends BasicMainMenuScreen {
    public MainMenuScreen() {
        super();

        Table table = new Table();

        table.setFillParent(true);

        Array<TextButton> buttons = new Array<>();

        TextButton playButton = new TextButton("PLAY", Render.skin);
        TextButton optionsButton = new TextButton("OPTIONS", Render.skin);
        TextButton quitButton = new TextButton("QUIT", Render.skin);

        playButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                backgroundSong.fadeOut(FADE_TIME);
                Render.setScreen(new GameScreen());
            }
        });

        optionsButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Render.setScreen(new OptionsScreen());
            }
        });

        quitButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        buttons.addAll(
                playButton,
                optionsButton,
                quitButton
        );

        buttons.forEach(b -> {
            table.add(b).center().minWidth(100f).minHeight(25f);
            table.row();
        });

        stage.addActor(table);
    }
}
