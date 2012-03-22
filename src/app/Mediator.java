package app;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import web.Authenticator;
import web.Communicator;
import web.GroupManager;
import web.Group;
import gui.ColorChooser;
import gui.GeneralGui;
import gui.GroupTab;
import gui.Gui;
import gui.drawings.Drawing;

public class Mediator {
	Vector<Drawing> drawings;
	StateManager stateMgr;
	Authenticator a;
	Communicator comm;
	Gui gui;
	Hashtable<Object, GroupTab> groupTab;
	GroupManager man;
	String username;
	GeneralGui gg;
	public Mediator() {
		drawings = new Vector<Drawing>();
		stateMgr = new StateManager(this);
		a = new Authenticator(this);
		groupTab = new Hashtable<Object, GroupTab>();
		comm = new Communicator();
		man = new GroupManager();
	}

	public void loginSuccessful(String user) {
		gui.loginSuccessful(user);
		this.username = user;
		man.connectUser(user);
		gg.setUser(user);
	}

	public void setGui(Gui gui2) {
		gui = gui2;
	}

	public void logOut() {
		man.logOffUser(username);
		gui.logOut();
	}

	public void createGroup() {
		gui.groupDialog();
	}

	public boolean groupExists(String t) {
		return man.groupExists(t);
	}

	public void addGroup(String t) {
		man.addGroup(t, username); 
	}

	public TreeModel getTreeModel() {
		return man.getTreeModel();
	}

	public ListModel getListModel() {
		return man.getListModel();
	}

	public boolean login(String user, String pass) {
		return a.authenticate(user, pass);
	}


	//TODO - managementul pe taburi
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


	public void loginError() {
		// TODO Auto-generated method stub

	}

	public ListModel getLegendModel() {
		// TODO return legendModel from groupmanager
		return new DefaultListModel();
	}



	public void setGeneralGui(GeneralGui generalGui) {
		gg = generalGui;
	}
	
	public boolean userInGroup(String user, String group) {
		return man.inGroup(group, user);
	}

	/****** RIGHT CLICK MENU ******/
	
	public void addUserCommand() {
		String addedUser = gg.getSelectedUser();
		DefaultMutableTreeNode group = gg.getSelectedGroup();
		
		if (group == null) {
			gui.error("No group selected");
			return;
		}
		if (addedUser==null) {
			gui.error("No user selected");
			return;
		}
		man.addUserCommand(username, addedUser,(String) group.getUserObject());
	}
	
	public void joinGroupCommand() {
		DefaultMutableTreeNode group = gg.getSelectedGroup();
		if (group == null) {
			gui.error("No group selected");
			return;
		}
		new ColorChooser(man.getAvailableColors(group.toString()), this, username, group.toString());
	}

	public void leaveGroupCommand() {
		DefaultMutableTreeNode group = gg.getSelectedGroup();
		if (group == null) {
			gui.error("No group selected");
			return;
		}
		man.leaveGroupCommand(username, (String) group.getUserObject());
	}

	public void joinGroupCommand(String user, String group, Color c) {
		man.joinGroupCommand(user, group,c);
		 
	} 
}

