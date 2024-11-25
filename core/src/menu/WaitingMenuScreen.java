package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.net.threads.Client;
import game.screens.MultiplayerGameScreen;
import utilities.Label;
import utilities.Render;

public class WaitingMenuScreen extends BasicMainMenuScreen {
    private final Label label;

    public WaitingMenuScreen() {
        MultiplayerGameScreen.client = new Client();
        MultiplayerGameScreen.client.start();

        Table table = new Table();
        table.setFillParent(true);

        label = new Label();

        table.add(label);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (MultiplayerGameScreen.client.isConnected()) {
            label.setText("Esperando a otro jugador...");
        } else {
            label.setText("El cliente no esta conectado a un servidor. (Posiblemente no esta en linea el servidor)");
        }

        if (Render.startGame) {
            backgroundSong.fadeOut(FADE_TIME);
            Render.setScreenToGame(MultiplayerGameScreen.class);
        }
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            MultiplayerGameScreen.client.end();
            Render.setScreen(new MainMenuScreen());
        }
    }
}
