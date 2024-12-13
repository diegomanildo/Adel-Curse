package com.ac;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import game.net.Client;
import game.screens.MultiplayerGameScreen;

public class DesktopLauncher {
	public static void main(String[] args) {
		try {
			Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
			config.setForegroundFPS(60);
			config.setTitle("Adel's Curse");
			config.setResizable(false);
			config.setWindowedMode(1280, 720);
			new Lwjgl3Application(new Juego(), config);
			onEndsProgram();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void onEndsProgram() {
		if (MultiplayerGameScreen.client != null) {
			MultiplayerGameScreen.client.socket.close();
		} else {
			MultiplayerGameScreen.client = new Client();
			MultiplayerGameScreen.client.socket.close();
		}
	}
}
