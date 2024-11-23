package com.ac;

import game.net.threads.ServerThread;

public class ServerLauncher {
	public static void main(String[] args) {
		ServerThread sv = new ServerThread();
		sv.start();
	}
}
