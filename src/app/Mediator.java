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
import gui.ColorChooser;
import gui.GeneralGui;
import gui.GroupTab;
import gui.Gui;
import gui.JCanvas;
import gui.buttons.ToolbarButton;
import gui.drawings.Drawing;

public class Mediator {
	Authenticator a;
	Communicator comm;
	Gui gui;
	Hashtable<Object, GroupTab> groupTab;
	GroupManager man;
	String username;
	GeneralGui gg;
	Vector<Tab> tabs;
	Tab currentTab;
	
	public Mediator() {
		a = new Authenticator(this);
		groupTab = new Hashtable<Object, GroupTab>();
		comm = new Communicator();
		man = new GroupManager();
		tabs = new Vector<Tab>();
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
	}

	public void createGroup() {
		gui.groupDialog();
		
	}

	public boolean groupExists(String t) {
		return man.groupExists(t);
	}

	
	
	public void addGroup(String t) {
		man.addGroup(t, username); 
		DefaultListModel l = man.getGroupLegend(t);
		GroupTab tb = gg.addTab(t,l);
		currentTab = new Tab(t,this);
		currentTab.jc = tb.panel;
		currentTab.tab = tb;
		tabs.add(currentTab);
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

	private Tab getTab(String name) {
		for (Tab t: tabs)
			if (t.getName().equals(name))
				return t;
		return null;
	}
	
	private Tab getCurrentTab() {
		for (Tab t: tabs)
			if (t.tab.equals(gg.getActiveTab()))
					return t;
		return null;
	}
	
	//TODO - managementul pe taburi
	public void addDrawing(Drawing d) {
		getCurrentTab().drawings.addElement(d);
	}

	public void mousePressed(int x, int y) {
		getCurrentTab().stateMgr.mousePressed(x, y);
		repaint();
	} 

	public void mouseDragged(int x, int y) {
		getCurrentTab().stateMgr.mouseDragged(x, y);
		repaint();
	}
	
	public void mouseReleased(int x, int y) {
		getCurrentTab().stateMgr.mouseReleased(x, y);
		repaint();
	}

	public void repaint(){
		if (getCurrentTab()!=null)
			getCurrentTab().jc.repaint();
		else
			System.out.println("NULL");
	}
	
	public void repaintSelected(int index) {
		tabs.get(index).repaint();
	}

	public void reDraw(Graphics g) {
		System.out.println(currentTab.getName());
		getCurrentTab().reDraw(g);
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
		getCurrentTab().stateMgr.setState(((ToolbarButton)o).getType());
	}


	public void loginError() {
		gui.error("Invalid credentials");
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
		String ret = man.addUserCommand(username, addedUser,(String) group.getUserObject());
		if (ret!=null)
			gui.error(ret);
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
		gg.closeTab(this.getTab((String) group.getUserObject()).tab);
		tabs.remove(this.getTab((String) group.getUserObject()));
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
		tab.printText(username,text,fontSize,fontColor);
		
	}

	public void saveWork() {
		// TODO Auto-generated method stub 
	}
	
}

