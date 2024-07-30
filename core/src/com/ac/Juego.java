package com.ac;

import com.badlogic.gdx.Game;
import utilities.Render;
import utilities.io.Channels;

public final class Juego extends Game {
	@Override
	public void create() {
		Render.app = this;
		Render.app.setScreen(new LibgdxObjectsScreen());
		Channels.setChannelVolume("Sfx", 1f);
		Channels.setChannelVolume("Music", 0f);

	}
}