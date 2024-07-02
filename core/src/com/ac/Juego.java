package com.ac;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import utilities.io.IOProcessor;

public class Juego extends Game {
	@Override
	public void create() {
		Render.io = new IOProcessor();
		Gdx.input.setInputProcessor(Render.io);

		Render.app = this;
		Render.batch = new SpriteBatch();

		setScreen(new MenuScreen());
	}
}
