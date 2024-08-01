package com.ac;

import com.badlogic.gdx.Game;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Settings;
import utilities.io.Channels;

public final class Juego extends Game {
	@Override
	public void create() {
		Settings.applySettings(Settings.getSettings());
		Channels.setChannelVolume("Sfx", 1f);
		Channels.setChannelVolume("Music", 0f);
		Render.app = this;
		Render.app.setScreen(new MainMenuScreen());
	}
}