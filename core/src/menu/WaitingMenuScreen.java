package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.Game;
import game.screens.MultiplayerGameScreen;
import utilities.Label;
import utilities.Render;

public class WaitingMenuScreen extends BasicMainMenuScreen {
    public WaitingMenuScreen() {
        Table table = new Table();
        table.setFillParent(true);

        table.add(new Label("Esperando a otro jugador..."));

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (Render.startGame) {
            backgroundSong.fadeOut(FADE_TIME);
            Render.setScreen(new Game(MultiplayerGameScreen.class));
        }
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Render.setScreen(new MainMenuScreen());
        }
    }
}
