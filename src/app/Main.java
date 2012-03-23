package app;

import gui.Gui;

public class Main {

	public static void main(String []args) {
		Mediator m = new Mediator();
		new Gui(m);
	}
}
