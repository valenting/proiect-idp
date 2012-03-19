package app;
import web.Authenticator;
import java.util.Vector;

import gui.Gui;
import gui.drawings.Drawing;
public class Mediator {
	Vector<Drawing> drawings;
	StateManager stateMgr;
	Authenticator a;
	Gui gui;
	public Mediator() {
		drawings = new Vector<Drawing>();
		stateMgr = new StateManager(this);
		a = new Authenticator(this);
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
	
	
	public boolean login(String user, String pass) {
		return a.authenticate(user, pass);
	}
	
	private void repaint(){
		//TODO
	}

	public void loginSuccessful(String user) {
		// TODO Auto-generated method stub
		gui.loginSuccessful(user);
	}

	public void loginError() {
		// TODO Auto-generated method stub
		
	}

	public void setGui(Gui gui2) {
		// TODO Auto-generated method stub
		gui = gui2;
	}

	public void logOut() {
		gui.logOut();
	}


}

