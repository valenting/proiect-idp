package app;

import java.awt.Color;
import java.awt.Graphics;
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
import gui.JCanvas;
import gui.buttons.ToolbarButton;
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
	Tab tabs;
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
		gg.logOut();
		// tabs.empty?
	}

	public void createGroup() {
		gui.groupDialog();
		
	}

	public boolean groupExists(String t) {
		return man.groupExists(t);
	}

	JCanvas jc;
	
	public void addGroup(String t) {
		man.addGroup(t, username); 
		DefaultListModel l = man.getGroupLegend(t);
		GroupTab tb = gg.addTab(t,l);
		jc = tb.panel;
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

	private void repaint(){
		jc.repaint();
	}

	public void reDraw(Graphics g) {
		g.setColor(Color.black);
		for (int i = 0; i < drawings.size(); i++) {
			Drawing v = (Drawing) drawings.elementAt(i);
			v.draw(g);
		}
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
		stateMgr.setState(((ToolbarButton)o).getType());
		// TODO - set state pe stateMgr al tabului
	}


	public void loginError() {
		// TODO Auto-generated method stub

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
		DefaultListModel l = man.getGroupLegend(group);
		gg.addTab(group,l);
	}

	public void sendText(String text, int fontSize, Color fontColor,
			GroupTab tab) {
		if (text.length()==0)
			return; 
		// TODO send to communicator
		System.out.println("not 0");
		tab.printText(username+": "+text+"\n",fontSize,fontColor);
		
	} 
}

