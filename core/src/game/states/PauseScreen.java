package game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import game.Game;
import game.GameScreen;
import menu.config.SettingsScreen;
import utilities.Render;
import utilities.SubScreen;
import utilities.TextButton;
import utilities.Timer;

public class PauseScreen extends SubScreen {
    private final Game game;
    private final Timer timer;

    public PauseScreen(Game game, Runnable backFunction, Timer timer) {
        super();
        this.game = game;
        this.timer = timer;

        setShow(false);

        Table table = new Table();
        table.setFillParent(true);

        Array<TextButton> buttons = new Array<>();

        buttons.addAll(
                new TextButton(Render.currentLanguage.resumeBtn(), () -> setShow(false)),
                new TextButton(Render.currentLanguage.settingsBtn(), () -> {
                    backFunction.run();
                    Render.setScreen(new SettingsScreen(new GameScreen()));
                }),
                new TextButton(Render.currentLanguage.backToMenuBtn(), backFunction)
        );

        buttons.forEach(b -> {
            table.add(b).center().width(200f).height(45f).padBottom(10f);
            table.row();
        });

        stage.addActor(table);
    }

    @Override
    public void setShow(boolean show) {
        super.setShow(show);
        if (show) {
            game.pause();
            timer.pause();
        } else {
            game.resume();
            timer.resume();
        }
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            setShow(false);
        }
    }
}
