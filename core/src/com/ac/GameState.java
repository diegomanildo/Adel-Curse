package com.ac;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameState {
    public static ExtendViewport viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public static OrthographicCamera camera = new OrthographicCamera(viewport.getScreenWidth(), viewport.getScreenHeight());
}
