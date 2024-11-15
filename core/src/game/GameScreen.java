package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Matrix4;
import game.hud.Hud;
import menu.BasicMainMenuScreen;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.SaveFile;
import utilities.Screen;

public final class GameScreen extends Screen {
    public static ChatScreen chat;
    public static Game game;
    public static Hud hud;
    public static PauseScreen pauseScreen;
    public static ShopScreen shopScreen;
    public static DeathScreen deathScreen;
    private static Matrix4 oldShapeRendererMatrix;

    public GameScreen(SaveFile saveFile) {
        super();
        chat = new ChatScreen();
        game = saveFile != null && saveFile.hasGame() ? saveFile.getGame() : new Game(saveFile);
        hud = new Hud();
        pauseScreen = new PauseScreen(GameScreen::exit);
        shopScreen = new ShopScreen();
        deathScreen = new DeathScreen(GameScreen::exit);

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
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
            game.getPlayer().setVelocity(game.getPlayer().getVelocity() * 2f);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            game.getPlayer().damage(1);
        }
    }

    public static void exit() {
        Render.sr.setProjectionMatrix(oldShapeRendererMatrix);

        game.getLevel().getSong().fadeOut(FADE_TIME);
        BasicMainMenuScreen.backgroundSong.fadeIn(FADE_TIME, true);
        Render.setScreen(new MainMenuScreen());
    }
}
