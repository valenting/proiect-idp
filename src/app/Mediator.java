package app;

import java.util.Vector;

import gui.Drawing;
public class Mediator {
	Vector<Drawing> drawings;
	StateManager stateMgr;
	
	public Mediator() {
		drawings = new Vector<Drawing>();
		stateMgr = new StateManager(this);
	}
	
	public void addDrawing(Drawing d) {
		drawings.addElement(d);
	}
	
	public void mouseClick(int x, int y) {
		stateMgr.mouseClick(x, y);
		repaint();
	}

	public void mouseMove(int x, int y) {
		stateMgr.mouseMove(x, y);
		repaint();
	}
	
	private void repaint(){
		//TODO
	}
}

