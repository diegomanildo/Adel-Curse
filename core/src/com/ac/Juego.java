package com.ac;

import com.badlogic.gdx.Game;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Settings;

public final class Juego extends Game {
	@Override
	public void create() {
		Settings.applySettings(Settings.getSettings());
		Render.app = this;
		Render.app.setScreen(new MainMenuScreen());
	}
}