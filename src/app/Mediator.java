package app;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.SelectionKey;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

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

import web.Authenticator;
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
		comm.connect("127.0.0.1", 7777);
		tabs = new Vector<Tab>();
		treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("Groups"));
	}

	public void login(String user, String pass) {
		this.username = user;
		comm.send(new LogInMessage(user,pass));
		// TODO set timer for resend
	}

	public void loginSuccessful() {
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
		getCurrentTab().stateMgr.setState(((ToolbarButton)o).getType()); // TODO create metod
	}


	public void loginError() {
		gui.error("Invalid credentials");
		logOut();
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

	/*
	public void joinGroupCommandRequest(final String user, final String group, final Color c) {
		if (!man.inGroup(user, group) && user!=null && group!=null) {
			JOptionPane.showMessageDialog(null, "Awaiting permission from the group's owner", "Notice", JOptionPane.PLAIN_MESSAGE);
			// TODO Stage 2: call communicator instead of accepting automatically
			Thread t = (new Thread() {
				public void run() {
					try {
						System.out.println("Requesting access");
						Thread.sleep(5000);
						joinGroupAccepted(user, group, c);

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
			t.start();
		}
	}
	 */

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
		
		// TODO set color

		comm.send(new GetGroupLegend(group));
		comm.send(new GetGroupDrawings(group));
		comm.send(new GetGroupHistory(group));
	}


	public void joinGroupDenied(String user, String group, Color c) {
		// Message - You have been denied joining the group
		// Unused at this stage
	}

	public void joinGroupEvent(String user, String group, Color c) {
		// Open a JPerm panel and send back answer
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
		gg.getTab(groupName).setLegend(model);
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
		getTab(group).addDrawing(d);
	}
}

