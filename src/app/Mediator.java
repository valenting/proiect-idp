package app;
import web.Authenticator;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import gui.GroupTab;
import gui.Gui;
import gui.drawings.Drawing;
public class Mediator {
	Vector<Drawing> drawings;
	StateManager stateMgr;
	Authenticator a;
	Gui gui;
	DefaultListModel userListModel;
	Hashtable<Object, GroupTab> groupTab;
	public Mediator() {
		drawings = new Vector<Drawing>();
		stateMgr = new StateManager(this);
		a = new Authenticator(this);
		groupTab = new Hashtable<Object, GroupTab>();
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
		gui.loginSuccessful(user);
		userListModel.addElement(user); // TODO authenticator/communicator should do this
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

	public void setUserModel(DefaultListModel mod) {
		userListModel = mod;
	}

	public void addGroupElement(Object o, GroupTab t) {
		groupTab.put(o, t);
	}
	
	public GroupTab getGroupTab(Object o) {
		return groupTab.get(o);
	}
	
	public void menuSelection(Object o) {
		System.out.println("menu selection");
		GroupTab t = getGroupTab(o);
		t.popOthers(o);
		// TODO - set state pe stateMgr al tabului
		
	}
}

