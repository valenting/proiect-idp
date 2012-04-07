package client;

import java.io.IOException;

import app.Log;
import app.Mediator;
import gui.Gui;

public class Client {
	private static String serverIP = "127.0.0.1";
	
	public static void main(String []args) throws IOException {
		if (args.length != 0) {
			serverIP = args[0];
		}
		Log.init();
		Mediator m = new Mediator(serverIP);
		new Gui(m);
	}
}

