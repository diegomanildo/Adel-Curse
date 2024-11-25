package com.ac;

import com.badlogic.gdx.Game;
import menu.LoadingScreen;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Settings;

public final class Juego extends Game {
	private final boolean debug = true;

	@Override
	public void create() {
		Settings.applySettings(Settings.getSettings());
		Render.app = this;
		Render.app.setScreen(debug ? new MainMenuScreen() : new LoadingScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		Render.sr.dispose();
	}
}