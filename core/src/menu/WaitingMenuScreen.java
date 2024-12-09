package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.net.Client;
import game.screens.MultiplayerGameScreen;
import menu.config.BasicOptionsScreen;
import utilities.Label;
import utilities.Render;
import utilities.Screen;

// CLASE PARA ESPERAR Y CONECTAR A LOS DOS JUGADORES
public class WaitingMenuScreen extends BasicOptionsScreen {
    private final Label label;

    public WaitingMenuScreen(Screen backScreen) {
        super(backScreen);
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

            if (Render.startGame) {
                Render.startGame = false;
                backgroundSong.fadeOut(FADE_TIME);
                Render.setScreenToGame(MultiplayerGameScreen.class);
            }
        } else {
            label.setText("El cliente no esta conectado a un servidor. (Posiblemente no esta en linea el servidor)");
        }
    }

    @Override
    protected String getTitleScreen() {
        return "";
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if(MultiplayerGameScreen.client.isConnected()){
                MultiplayerGameScreen.client.end();
            }
            Render.setScreen(new MainMenuScreen());
        }
    }
}
