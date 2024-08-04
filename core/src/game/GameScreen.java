package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import game.states.Game;
import game.states.PauseScreen;
import game.states.hud.Hud;
import menu.BasicMainMenuScreen;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Screen;

public final class GameScreen extends Screen {
    private final Game game;
    private final Hud hud;
    private final PauseScreen pause;

    public GameScreen() {
        super();

        game = new Game();
        hud = new Hud(game.getPlayer());
        pause = new PauseScreen(game, this::exit, hud.getTimer());

        addSubScreen(game);
        addSubScreen(hud);
        addSubScreen(pause);

        if (Render.isDebugging()) {
            stage.getCamera().zoom = 1.5f;
        }
    }

    @Override
    public void show() {
        super.show();
        BasicMainMenuScreen.backgroundSong.fadeOut(FADE_TIME);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            pause.setShow(true);
        }
    }

    public void exit() {
        game.getSong().fadeOut(FADE_TIME);
        BasicMainMenuScreen.backgroundSong.fadeIn(FADE_TIME, true);
        Render.setScreen(new MainMenuScreen());
    }
}
