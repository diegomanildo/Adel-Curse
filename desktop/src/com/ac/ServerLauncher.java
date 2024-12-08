package com.ac;

import game.net.Server;

public class ServerLauncher {
	public static void main(String[] args) {
		Server sv = new Server();
		sv.start();
	}
}
