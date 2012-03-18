package app;

import java.util.Vector;

import gui.Drawing;
public class Mediator {
	Vector<Drawing> drawings;
	
	public Mediator() {
		drawings = new Vector<Drawing>();
	}
	
	public void addDrawing(Drawing d) {
		drawings.addElement(d);
	}
}
