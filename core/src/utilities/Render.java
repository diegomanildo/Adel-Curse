package utilities;

import com.ac.Juego;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import languages.Language;
import languages.SpanishLanguage;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class Render {
    public static Size screenSize = new Size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public static Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    public static Juego app;
    public static Language currentLanguage = new SpanishLanguage();
    public static ShapeRenderer sr = new ShapeRenderer();

    public static int fps = 60;

    private static boolean debug = false;

    public static boolean isDebugging() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        for (String arg : runtimeMXBean.getInputArguments()) {
            if (arg.contains("-agentlib:jdwp")) {
                return true;
            }
        }

        return debug;
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
