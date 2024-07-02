package com.ac;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import utilities.io.IOProcessor;

public class Render {
    public static SpriteBatch batch;
    public static Juego app;
    public static IOProcessor io;

    public static void clear(float r, float g, float b, float a) {
        ScreenUtils.clear(r, g, b, a);
    }

    public static void clear() {
        clear(0f, 0f, 0f, 1f);
    }

    public static float getMiddleX() {
        return (float) Gdx.graphics.getWidth() / 2;
    }

    public static float getMiddleY() {
        return (float) Gdx.graphics.getHeight() / 2;
    }
}
