package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Matrix4;
import game.entities.items.Mushroom;
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

        try {
            Game.game = game.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        chat = new ChatScreen();
        hud = new Hud();
        pauseScreen = new PauseScreen();
        shopScreen = new ShopScreen();
        deathScreen = new DeathScreen();
        winScreen = new WinScreen();

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
            game.getPlayer().setVelocity(game.getPlayer().getVelocity() * 2f);
            game.getPlayer().setDamage(50);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            game.getPlayer().damage(1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            game.getPlayer().addItem(new Mushroom());
        }
    }

    public static void restart() {
        if (MultiplayerGameScreen.client != null && !MultiplayerGameScreen.client.isSendingData()) {
            MultiplayerGameScreen.client.restart();
        }
        Render.setScreenToGame();
    }

    public static void exit() {
        Render.sr.setProjectionMatrix(oldShapeRendererMatrix);
        changeSong();
        Gdx.app.postRunnable(() -> {
            if (MultiplayerGameScreen.client != null) {
                MultiplayerGameScreen.client.end();
                GameData.clientNumber = -1;
                GameData.networkListener = null;
                MultiplayerGameScreen.client = null;
            }
            Render.setScreen(new MainMenuScreen());
        });
    }

    public static void changeSong() {
        game.getLevel().getSong().fadeOut(FADE_TIME);
        BasicMainMenuScreen.backgroundSong.fadeIn(FADE_TIME, true);
    }
}
