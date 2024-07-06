package com.ac;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import screens.MenuScreen;
import utilities.Render;
import utilities.io.IOProcessor;

public class Juego extends Game {
	@Override
	public void create() {
		Render.io = new IOProcessor();
		Render.b = new SpriteBatch();
		Render.app = this;
		Render.app.setScreen(new MenuScreen());
		Gdx.input.setInputProcessor(Render.io);
	}
}