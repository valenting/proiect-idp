package app;

import gui.GroupTab;
import gui.JCanvas;
import gui.drawings.Drawing;

import java.awt.Graphics;
import java.util.Vector;

public class Tab {
	String name;
	StateManager stateMgr;
	GroupTab tab;
	Mediator m;
	Vector<Drawing> drawings;
	Mediator med;
	JCanvas jc;
	
	public Tab(String name, Mediator m) {
		this.name = name;
		med  = m;
		stateMgr = new StateManager(m);
	}
	
	public void setDrawings(Vector<Drawing> v ) {
		drawings = v;
	}
	
	public String getName() {
		return name;
	}
	
	public void addDrawing(Drawing d) {
		drawings.addElement(d);
	}

	public void setCanvas(JCanvas canvas) {
		jc = canvas;
	}
	
	public void setGroupTab(GroupTab t) {
		tab = t;
	}
	
	public void mousePressed(int x, int y) {
		stateMgr.mousePressed(x, y);
		repaint();
	} 

	public void mouseDragged(int x, int y) {
		stateMgr.mouseDragged(x, y);
		repaint();
	}
	
	public void mouseReleased(int x, int y) {
		stateMgr.mouseReleased(x, y);
		repaint();
	}

	public void repaint(){
		jc.repaint();
	}

	public void reDraw(Graphics g) {
		for (int i = 0; i < drawings.size(); i++) {
			Drawing v = (Drawing) drawings.elementAt(i);
			v.draw(g);
		}
	}
}
