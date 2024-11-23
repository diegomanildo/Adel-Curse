package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.Game;
import game.net.threads.ClientThread;
import game.screens.MultiplayerGameScreen;
import utilities.Label;
import utilities.Render;
import utilities.Utils;

public class WaitingMenuScreen extends BasicMainMenuScreen {
    private final Label label;

    public WaitingMenuScreen() {
        MultiplayerGameScreen.client = new ClientThread();
        MultiplayerGameScreen.client.start();

        Table table = new Table();
        table.setFillParent(true);

        Utils.sleep(500); // Sleep time for connect client
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
            Render.setScreen(new Game(MultiplayerGameScreen.class));
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
