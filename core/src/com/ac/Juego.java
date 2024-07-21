package com.ac;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import gameUtilities.GameObject;
import menu.MainMenuScreen;
import utilities.Render;

public final class Juego extends Game {
	@Override
	public void create() {
		Render.app = this;
		Render.setScreen(new MainMenuScreen(), true);
		Gdx.input.setInputProcessor(Render.io);
	}
}