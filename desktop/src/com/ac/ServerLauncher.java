package com.ac;

public class ServerLauncher {
	public static void main(String[] args) {
		String[] newArgs = new String[args.length + 1];
		for (int i = 0; i < args.length; i++) {
			newArgs[i] = args[i];
		}

		newArgs[args.length] = "server";

		DesktopLauncher.main(newArgs);
	}
}
