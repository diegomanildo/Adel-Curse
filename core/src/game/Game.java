package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Matrix4;
import game.entities.characters.playables.Adel;
import game.hud.Hud;
import game.screens.*;
import menu.BasicMainMenuScreen;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Screen;

public final class Game extends Screen {
    public static ChatScreen chat;
    public static GameScreen game;
    public static Hud hud;
    public static PauseScreen pauseScreen;
    public static ShopScreen shopScreen;
    public static DeathScreen deathScreen;
    private static Matrix4 oldShapeRendererMatrix;

    public Game() {
        super();
        chat = new ChatScreen();
        game = new GameScreen();
        Adel p1 = new Adel();
        p1.setPosition(game.getLevel().getInitX() - p1.getWidth() / 2f, game.getLevel().getInitY() - p1.getHeight() / 2f);
        game.createPlayer(p1);
        hud = new Hud();
        pauseScreen = new PauseScreen(Game::exit);
        shopScreen = new ShopScreen();
        deathScreen = new DeathScreen(Game::exit);

        addSubScreen(game);
        addSubScreen(chat);
        addSubScreen(hud);
        addSubScreen(shopScreen);
        addSubScreen(pauseScreen);
        addSubScreen(deathScreen);
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
        }
    }

    public static void exit() {
        Render.sr.setProjectionMatrix(oldShapeRendererMatrix);

        game.getLevel().getSong().fadeOut(FADE_TIME);
        BasicMainMenuScreen.backgroundSong.fadeIn(FADE_TIME, true);
        Render.setScreen(new MainMenuScreen());
    }
}
