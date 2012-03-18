package app;

import gui.Gui;

public class Main {

	public static void main(String []args) {
		System.out.println("hello from main");
		// TODO
		Mediator m = new Mediator();
		new Gui(m);
	}
}
