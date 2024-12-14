package com.ac;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import game.net.Client;
import game.screens.MultiplayerGameScreen;

import java.util.Arrays;

public class DesktopLauncher {
	private static Juego juego;

	public static void main(String[] args) {
		boolean isServer = Arrays.stream(args).anyMatch(arg -> arg.toLowerCase().contains("server"));

		juego = new Juego(isServer);
        if (!isServer) {
            initGame();
        } else {
            initServer();
        }
    }

	private static void initGame() {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Adel's Curse");
		config.setResizable(false);
		config.setWindowedMode(1280, 720);

		new Lwjgl3Application(juego, config);

		if (MultiplayerGameScreen.client != null) {
			MultiplayerGameScreen.client.socket.close();
		} else {
			MultiplayerGameScreen.client = new Client();
			MultiplayerGameScreen.client.socket.close();
		}
	}

	private static void initServer() {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Adel's Curse Server");
		config.setWindowedMode(600, 600);

		new Lwjgl3Application(juego, config);
	}
}
