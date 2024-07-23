package utilities;

import com.ac.Juego;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import utilities.io.IOProcessor;

public class Render {
    public static final int MIN_WIDTH = 800;
    public static final int MIN_HEIGHT = 600;
    public static SpriteBatch b = new SpriteBatch();
    public static IOProcessor io = new IOProcessor();
    public static Size screenSize = new Size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    public static Juego app;
    public static AssetManager manager = new AssetManager();

    public static OrthographicCamera camera = new OrthographicCamera();

    public final static boolean DEBUG_MODE = false;

    public static void clear(float r, float g, float b, float a) {
        ScreenUtils.clear(r, g, b, a);
    }

    public static void clear(Color c) {
        clear(c.r, c.g, c.b, c.a);
    }

    public static void clear() {
        clear(0f, 0f, 0f, 1f);
    }

    public static float getMiddleX() {
        return screenSize.width / 2f;
    }

    public static float getMiddleY() {
        return screenSize.height / 2f;
    }

    public static void setScreen(Screen s, boolean firstTime) {
        if (!firstTime) {
            app.getScreen().dispose();
        }
        app.setScreen(s);
        s.initializeManagers();

        if (!firstTime) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void setScreen(Screen s) {
        setScreen(s, false);
    }
}
