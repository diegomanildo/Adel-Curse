package utilities;

import com.ac.Juego;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import screens.Screen;
import utilities.io.IOProcessor;

public class Render {
    public static SpriteBatch b;
    public static Juego app;
    public static IOProcessor io;
    public static Size screenSize = new Size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    public static void clear(float r, float g, float b, float a) {
        ScreenUtils.clear(r, g, b, a);
    }

    public static void clear() {
        clear(0f, 0f, 0f, 1f);
    }

    public static float getMiddleX() {
        return Gdx.graphics.getWidth() / 2f;
    }

    public static float getMiddleY() {
        return Gdx.graphics.getHeight() / 2f;
    }

    public static void setScreen(Screen s) {
        app.getScreen().dispose();
        app.setScreen(s);
    }
}
