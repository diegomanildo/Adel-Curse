package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Matrix4;
import game.hud.HudScreen;
import game.items.Crucifix;
import game.net.GameData;
import game.screens.*;
import menu.BasicMainMenuScreen;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Screen;

public final class Game extends Screen {
    public static AbstractGameScreen game;
    public static ChatScreen chat;
    public static HudScreen hudScreen;
    public static ItemPickedScreen itemPickedScreen;
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
        hudScreen = new HudScreen();
        itemPickedScreen = new ItemPickedScreen();
        pauseScreen = new PauseScreen();
        shopScreen = new ShopScreen();
        deathScreen = new DeathScreen();
        winScreen = new WinScreen();

        addSubScreen(Game.game);
        addSubScreen(chat);
        addSubScreen(hudScreen);
        addSubScreen(itemPickedScreen);
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
            game.getPlayer().addDamage(50);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            game.allPlayers.get(GameData.clientNumber).damage(Integer.MAX_VALUE);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F3) && Game.game instanceof MultiplayerGameScreen) {
            game.allPlayers.get(GameData.clientNumber).addItem(new Crucifix());
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
