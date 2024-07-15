package com.ac;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import screens.MainMenuScreen;
import utilities.Render;

public final class Juego extends Game {
	@Override
	public void create() {
		Render.app = this;
		Render.app.setScreen(new MainMenuScreen());
		Gdx.input.setInputProcessor(Render.io);
	}
}