package com.ac;

import com.badlogic.gdx.Game;
import game.net.Server;
import menu.LoadingScreen;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Settings;

public final class Juego extends Game {
	private final boolean isServerScreen;
    public Server server;

	public Juego(boolean isServerScreen) {
		this.isServerScreen = isServerScreen;
	}

	@Override
	public void create() {
        Render.app = this;
        if (isServerScreen) {
            ServerConsoleScreen serverConsoleScreen = new ServerConsoleScreen();
            server = new Server(serverConsoleScreen);
            server.start();
            Render.app.setScreen(new LoadingScreen(serverConsoleScreen));
        } else {
            Settings.applySettings(Settings.getSettings());
            Render.app.setScreen(new LoadingScreen(new MainMenuScreen()));
        }
    }

	@Override
	public void dispose() {
		super.dispose();
        Render.sr.dispose();
    }
}