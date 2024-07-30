package com.ac;

import com.badlogic.gdx.Game;
import game.GameScreen;
import utilities.Render;
import utilities.io.Channels;

public final class Juego extends Game {
	@Override
	public void create() {
		Channels.setChannelVolume("Sfx", 1f);
		Channels.setChannelVolume("Music", 0.1f);
		Render.app = this;
		Render.app.setScreen(new GameScreen());
	}
}