package com.ac;

import com.badlogic.gdx.Game;
import game.net.threads.ClientThread;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Settings;

public final class Juego extends Game {
	@Override
	public void create() {
		ClientThread client = new ClientThread();
		client.start();


		Settings.applySettings(Settings.getSettings());
		Render.app = this;
		Render.app.setScreen(new MainMenuScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		Render.sr.dispose();
	}
}