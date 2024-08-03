package game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import game.hud.Hud;
import game.states.PauseScreen;
import menu.BasicMainMenuScreen;
import utilities.Screen;

public final class GameScreen extends Screen {
    private final Game game;
    private final Hud hud;
    private final PauseScreen pause;

    public GameScreen() {
        super();

        game = new Game();
        hud = new Hud(game.getPlayer());
        pause = new PauseScreen(game, hud.getTimer());

        addSubScreen(game);
        addSubScreen(hud);
        addSubScreen(pause);

        stage.getViewport().setCamera(new OrthographicCamera());
    }

    @Override
    public void show() {
        super.show();
        BasicMainMenuScreen.backgroundSong.fadeOut(FADE_TIME);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (stage.isKeyPressed(Input.Keys.ESCAPE)) {
            loadPause();
        }
    }

    private void loadPause() {
        pause.setShow(true);
        hud.getTimer().pause();
    }
}
