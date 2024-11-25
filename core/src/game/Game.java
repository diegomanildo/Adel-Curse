package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Matrix4;
import game.hud.Hud;
import game.net.GameData;
import game.screens.*;
import menu.BasicMainMenuScreen;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Screen;

public final class Game extends Screen {
    public static AbstractGameScreen game;
    public static ChatScreen chat;
    public static Hud hud;
    public static PauseScreen pauseScreen;
    public static ShopScreen shopScreen;
    public static DeathScreen deathScreen;
    public static WinScreen winScreen;
    private static Matrix4 oldShapeRendererMatrix;

    public Game(Class<? extends AbstractGameScreen> game) {
        super();
        if (game == MultiplayerGameScreen.class) {
            System.out.println("You are client n°" + GameData.clientNumber);
        }
        if (Game.game == null) {
            try {
                Game.game = game.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        chat = new ChatScreen();
        hud = new Hud();
        pauseScreen = new PauseScreen(Game::exit);
        shopScreen = new ShopScreen();
        deathScreen = new DeathScreen(Game::exit);
        winScreen = new WinScreen(Game::exit);

        addSubScreen(Game.game);
        addSubScreen(chat);
        addSubScreen(hud);
        addSubScreen(shopScreen);
        addSubScreen(pauseScreen);
        addSubScreen(deathScreen);
        addSubScreen(winScreen);
    }

    @Override
    public void show() {
        super.show();
        BasicMainMenuScreen.backgroundSong.fadeOut(FADE_TIME);

        oldShapeRendererMatrix = Render.sr.getTransformMatrix();
        Render.sr.setProjectionMatrix(game.getLevel().getCamera().combined);
    }

    @Override
    protected void handleInput() {
        super.handleInput();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pauseScreen.setShow(true);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            pauseScreen.setShow(true);
        }
    }

    public static void exit() {
        Render.sr.setProjectionMatrix(oldShapeRendererMatrix);

        game.getLevel().getSong().fadeOut(FADE_TIME);
        BasicMainMenuScreen.backgroundSong.fadeIn(FADE_TIME, true);
        if (MultiplayerGameScreen.client != null) {
            MultiplayerGameScreen.client.end();
        }
        Gdx.app.postRunnable(() -> Render.setScreen(new MainMenuScreen()));
    }
}
