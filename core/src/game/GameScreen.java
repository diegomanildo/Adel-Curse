package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import game.hud.Hud;
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
        hud = new Hud(game.getPlayer(), game.getLevel().getMap());
        pause = new PauseScreen(game, this::exit, hud.getTimer());

        addSubScreen(game);
        addSubScreen(hud);
        addSubScreen(pause);
    }

    @Override
    public void show() {
        super.show();
        BasicMainMenuScreen.backgroundSong.fadeOut(FADE_TIME);
    }

    @Override
    protected void handleInput() {
        super.handleInput();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pause.setShow(true);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
            game.getPlayer().setVelocity(game.getPlayer().getVelocity() * 2f);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            game.getPlayer().damage(1);
        }
    }

    public void exit() {
        game.getSong().fadeOut(FADE_TIME);
        BasicMainMenuScreen.backgroundSong.fadeIn(FADE_TIME, true);
        Render.setScreen(new MainMenuScreen());
    }
}
