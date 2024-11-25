package com.ac;

import game.net.threads.Server;

public class ServerLauncher {
	public static void main(String[] args) {
		Server sv = new Server();
		sv.start();
	}
}
