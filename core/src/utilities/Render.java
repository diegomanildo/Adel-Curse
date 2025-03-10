package utilities;

import com.ac.Juego;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import game.Game;
import game.screens.AbstractGameScreen;
import game.screens.MultiplayerGameScreen;
import game.screens.OnePlayerGameScreen;
import game.utilities.AssetManager;

public class Render {
    private static Class<? extends AbstractGameScreen> lastGameClass;
    public static Size screenSize = new Size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public static Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    public static Juego app;
    public static ShapeRenderer sr = new ShapeRenderer();
    public static AssetManager assetManager;

    public static int fps = 60;
    public static boolean startGame = false;

    private static boolean debug = false;

    public static boolean isDebugging() {
        return Utils.isDebugging() || debug;
    }

    public static void setDebug(boolean debug) {
        Render.debug = debug;
    }

    public static void clear(float r, float g, float b, float a) {
        ScreenUtils.clear(r, g, b, a);
    }

    public static void clear(Color c) {
        clear(c.r, c.g, c.b, c.a);
    }

    public static Class<? extends AbstractGameScreen> getGameClass() {
        if (lastGameClass.equals(OnePlayerGameScreen.class)) {
            return OnePlayerGameScreen.class;
        } else if (lastGameClass.equals(MultiplayerGameScreen.class)) {
            return MultiplayerGameScreen.class;
        } else {
            throw new RuntimeException("Class " + lastGameClass.getSimpleName() + " not implemented");
        }
    }

    public static void setScreenToGame(Class<? extends AbstractGameScreen> gameClass) {
        if (gameClass != null) {
            lastGameClass = gameClass;
        }
        Class<? extends AbstractGameScreen> gClass = gameClass != null ? gameClass : getGameClass();
        Gdx.app.postRunnable(() -> setScreen(new Game(gClass)));
    }

    public static void setScreenToGame() {
        setScreenToGame(null);
    }

    public static void setScreen(Screen s, boolean firstTime) {
        if (!firstTime) {
            app.getScreen().dispose();
        }
        app.setScreen(s);
        Gdx.input.setInputProcessor(s.stage);
    }

    public static void setScreen(Screen s) {
        setScreen(s, false);
    }
}
