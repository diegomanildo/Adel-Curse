package com.ac;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import utilities.Render;

public class DesktopLauncher {
	public static void main(String[] args) {
		try {
			Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
			config.setForegroundFPS(60);
			config.setWindowSizeLimits(Render.MIN_WIDTH, Render.MIN_HEIGHT, 1920, 1080);
			config.setTitle("Adel's Curse");
			config.setResizable(false);
			config.setWindowedMode(1280, 720);
			new Lwjgl3Application(new Juego(), config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
