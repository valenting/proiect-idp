package client;

import app.Mediator;
import gui.Gui;

public class Client {

	public static void main(String []args) {
		Mediator m = new Mediator();
		new Gui(m);
	}
}
