package app;
import web.Authenticator;
import web.Communicator;
import web.GroupManager;

import web.Group;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.tree.TreeModel;

import gui.GroupTab;
import gui.GroupTree;
import gui.Gui;
import gui.drawings.Drawing;
public class Mediator {
	Vector<Drawing> drawings;
	StateManager stateMgr;
	Authenticator a;
	Communicator comm;
	Gui gui;
	Hashtable<Object, GroupTab> groupTab;
	//GroupTree groupTree;
	GroupManager man;
	String username;
	public Mediator() {
		drawings = new Vector<Drawing>();
		stateMgr = new StateManager(this);
		a = new Authenticator(this);
		groupTab = new Hashtable<Object, GroupTab>();
		comm = new Communicator();
		man = new GroupManager();
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
		this.username = user;
		man.connectUser(user);

		
	}

	public void loginError() {
		// TODO Auto-generated method stub
		
	}

	public void setGui(Gui gui2) {
		gui = gui2;
	}

	public void logOut() {
		for (Group g: man.getGroups())
			g.delUser(username);
		gui.logOut();
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

	public void createGroup() {
		gui.groupDialog();
	}


	public boolean groupExists(String t) {
		return man.groupExists(t);
	}

	public void addGroup(String t) {
		System.out.println(username);
		man.addGroup(t, username); 
	}
	
	public TreeModel getTreeModel() {
		return man.getTreeModel();
	}
	
	public ListModel getListModel() {
		return man.getListModel();
	}
}

