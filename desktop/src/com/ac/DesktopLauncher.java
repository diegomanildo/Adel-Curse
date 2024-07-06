package com.ac;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] args) {
		try {
			Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
			config.setForegroundFPS(60);
			config.setTitle("Adel's Curse");
			config.setMaximized(true);
			config.setResizable(true);
			new Lwjgl3Application(new Juego(), config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
