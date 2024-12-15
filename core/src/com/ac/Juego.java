package com.ac;

import com.badlogic.gdx.Game;
import game.net.Server;
import menu.LoadingScreen;
import utilities.Render;
import utilities.Settings;

public final class Juego extends Game {
	private final boolean isServerScreen;
	private ServerConsoleScreen serverConsoleScreen;
	public Server server;

	public Juego(boolean isServerScreen) {
		this.isServerScreen = isServerScreen;
	}

	@Override
	public void create() {
        if (isServerScreen) {
            serverConsoleScreen = new ServerConsoleScreen();
            server = new Server(serverConsoleScreen);
            server.start();
            setScreen(serverConsoleScreen);
        } else {
            Settings.applySettings(Settings.getSettings());
            Render.app = this;
            Render.app.setScreen(new LoadingScreen());
        }
    }

	@Override
	public void dispose() {
		super.dispose();
        Render.sr.dispose();
    }
}