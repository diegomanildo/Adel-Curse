package utilities;

import com.ac.Juego;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import game.utilities.Camera2D;
import utilities.io.IOProcessor;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class Render {
    public static final int MIN_WIDTH = 800;
    public static final int MIN_HEIGHT = 600;
    public static SpriteBatch b = new SpriteBatch();
    public static ShapeRenderer sr = new ShapeRenderer();
    public static IOProcessor io = new IOProcessor();
    public static Size screenSize = new Size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public static Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    public static Juego app;

    public static Camera2D camera = new Camera2D();

    public static boolean isDebugging() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        for (String arg : runtimeMXBean.getInputArguments()) {
            if (arg.contains("-agentlib:jdwp")) {
                return true;
            }
        }
        return false;
    }

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
