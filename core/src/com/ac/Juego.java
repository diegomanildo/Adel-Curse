package com.ac;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import game.GameScreen;
import utilities.Render;

public final class Juego extends Game {
	@Override
	public void create() {
		Render.app = this;
		Render.setScreen(new GameScreen(), true);
		Gdx.input.setInputProcessor(Render.io);
	}
}