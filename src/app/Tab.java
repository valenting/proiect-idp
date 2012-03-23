package app;

import gui.GroupTab;
import gui.drawings.Drawing;

import java.util.Vector;

public class Tab {
	StateManager stateMgr;
	GroupTab tab;
	Mediator m;
	Vector<Drawing> drawings;
	public Tab() {
		drawings = new Vector<Drawing>();
	}
}
