package app;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import network.*;
import network.c2s.AddUserMessage;
import network.c2s.ColorDialogMessage;
import network.c2s.DrawingMessage;
import network.c2s.GetGroupDrawings;
import network.c2s.GetGroupHistory;
import network.c2s.GetGroupLegend;
import network.c2s.JoinGroupMessage;
import network.c2s.LeaveGroupMessage;
import network.c2s.LogInMessage;
import network.c2s.LogOutMessage;
import network.c2s.NewGroupMessage;
import network.c2s.TextMessage;

import gui.ColorChooser;
import gui.GeneralGui;
import gui.GroupTab;
import gui.Gui;
import gui.buttons.ToolbarButton;
import gui.drawings.Drawing;

public class Mediator {
	Communicator comm;
	Gui gui;
	Hashtable<Object, GroupTab> groupTab;
	String username;
	GeneralGui gg;
	Vector<Tab> tabs;
	DefaultTreeModel treeModel;
	public Mediator() {
		groupTab = new Hashtable<Object, GroupTab>();
		comm = new Communicator(this);
		tabs = new Vector<Tab>();
		treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("Groups"));
	}

	public void login(String user, String pass) {
		this.username = user;
		comm.connect("192.168.1.7", 7777);
		// TODO if connection fails
		comm.send(new LogInMessage(user,pass));
	}

	public void loginSuccessful() {
		//TODO Create log file
		gui.loginSuccessful(username);
		gg.setUser(username);
	}

	public void logOut() {
		comm.send(new LogOutMessage(username));
		gui.logOut();
		gg.logOut();
		tabs.removeAllElements();
		username = "(null)";
	}

	public void setGui(Gui gui2) {
		gui = gui2;
	}

	public void createGroup() {
		gui.groupDialog();

	}

	public boolean groupExists(String group) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
		for (int i=0;i<root.getChildCount();i++) {
			DefaultMutableTreeNode n = (DefaultMutableTreeNode) root.getChildAt(i);
			if (n.getUserObject().equals(group))
				return true;
		}
		return false;
	}

	public void addGroup(String t) {
		comm.send(new NewGroupMessage(t, username));
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

	public void addDrawing(Drawing d) {
		getCurrentTab().addDrawing(d);
	}

	public void mousePressed(int x, int y) {
		getCurrentTab().mousePressed(x, y);
		getCurrentTab().repaint();
	} 

	public void mouseDragged(int x, int y) {
		getCurrentTab().mouseDragged(x, y);
		getCurrentTab().repaint();
	}

	public void mouseReleased(int x, int y) {
		getCurrentTab().mouseReleased(x, y);
		getCurrentTab().repaint();
	}


	public void reDraw(Graphics g) {
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
		getCurrentTab().setState(((ToolbarButton)o).getType()); 
	}


	public void loginError() {
		gui.error("Invalid credentials");
		gui.resetLogin();
	}

	public void setGeneralGui(GeneralGui generalGui) {
		gg = generalGui;
	}

	public boolean userInGroup(String user, String group) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
		for (int i=0;i<root.getChildCount();i++) {
			DefaultMutableTreeNode n = (DefaultMutableTreeNode) root.getChildAt(i);
			if (n.getUserObject().equals(group))
				for (int j=0;j<n.getChildCount();j++) {
					DefaultMutableTreeNode t = (DefaultMutableTreeNode) n.getChildAt(j);
					if (t.getUserObject().equals(user))
						return true;
				}
		}
		return false;
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
		comm.send(new AddUserMessage((String) group.getUserObject(), addedUser, username)); 
	}

	public void displayError(String s) {
		gui.error(s);
	}

	public void joinGroupCommand() {
		DefaultMutableTreeNode group = gg.getSelectedGroup();
		if (group == null) {
			gui.error("No group selected");
			return;
		}

		if (userInGroup(group.toString(), username))
			return;

		comm.send(new ColorDialogMessage(group.toString()));
	}

	public void openColorChooser(Vector<Color> colors, String group) {
		new ColorChooser(colors, this, username, group);
	}

	public void leaveGroupCommand() {
		DefaultMutableTreeNode group = gg.getSelectedGroup();
		if (group == null) {
			gui.error("No group selected");
			return;
		}
		System.out.println(group.getUserObject());
		gg.closeTab((String)group.getUserObject());
		tabs.remove(getTab(group.getUserObject().toString()));
		comm.send(new LeaveGroupMessage((String) group.getUserObject(), username));
	}

	public void joinGroupCommand(String user, String group, Color c) {

		if (userInGroup(user, group))
			return;
		comm.send(new JoinGroupMessage(group.toString(),username,c));
	}

	public void joinGroupAccepted(String user, String group) {
		if (!user.equals(username))
			return;
		GroupTab tb = gg.addTab(group,new DefaultListModel());
		Tab currentTab = new Tab(group,this);
		currentTab.setCanvas(tb.panel);
		currentTab.setGroupTab(tb);

		currentTab.setDrawings(new Vector<Drawing>());
		tb.setDocument(new DefaultStyledDocument());
		tabs.add(currentTab);
		
		System.err.println("grp: "+group);		
		comm.send(new GetGroupLegend(group));
		comm.send(new GetGroupDrawings(group));
		comm.send(new GetGroupHistory(group));
	}


	public void sendText(String text, int fontSize, Color fontColor, GroupTab tab) {
		if (text.length()==0)
			return; 
		String tabName = gg.getTabName(tab);
		comm.send(new TextMessage(tabName, username, text, fontSize, fontColor));
	}
	
	public void printText(String groupName, String userName, String text, int fontSize, Color fontColor) {
		Tab t = getTab(groupName);
		if (t != null) 
			t.tab.printText(userName, text, fontSize, fontColor);
	}

	public void saveWork() {
		// TODO Auto-generated method stub 
	}


	public void setUserList(DefaultListModel model) {
		gg.setListModel(model);
	}

	public void setTreeList(DefaultTreeModel model) {
		gg.setTreeModel(model);
		treeModel = model;
	}
	
	public void setUserLegend(String groupName, DefaultListModel model) {
		System.err.println("Set Legend");
		GroupTab t = gg.getTab(groupName);
		if (t!=null)
		t.setLegend(model);
	}
	
	public void setDrawings(String groupName, Vector<Drawing> drawings) {
		getTab(groupName).setDrawings(drawings);
		getTab(groupName).repaint();
	}
	
	public void setHistory(String groupName, DefaultStyledDocument document) {
		gg.getTab(groupName).setHistory(document);
	}

	public void sendDrawing(Drawing d) {
		getCurrentTab().delDrawing(d);
		comm.send(new DrawingMessage(username, getCurrentTab().getName(), d));
	}

	public void updateDrawings(String group, Drawing d) {
		System.err.println(group);
		System.err.println(d);
		Tab t = getTab(group);
		if (t!=null)
			getTab(group).addDrawing(d);
	}
}

