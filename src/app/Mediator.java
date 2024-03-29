package app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.ini4j.Ini;

import web.GoogleComm;
import web.MyIni;
import web.SimpleEncryption;
import web.XMLParser;


import network.*;
import network.c2s.AddUserMessage;
import network.c2s.C2SEmailMessage;
import network.c2s.ProbeGroupMessage;
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
import gui.GoogleLogin;
import gui.GroupTab;
import gui.Gui;
import gui.Timeline;
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
	String serverIP;
	GoogleComm gcom;
	Ini ini;
	String email = null;
	public Mediator(String serverIP) {
		groupTab = new Hashtable<Object, GroupTab>();
		comm = new Communicator(this);
		tabs = new Vector<Tab>();
		treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("Groups"));
		this.serverIP = serverIP;
		gcom = new GoogleComm(this);

		MyIni.open("google.ini");
	}

	public void login(String user, String pass) {
		this.username = user;
		boolean done = comm.connect(serverIP, 7777);
		if (done)
			comm.send(new LogInMessage(user,pass));
	}

	public void loginSuccessful() {
		Log.setLogFile(username + ".log");
		Log.debug("Cient " + username + " connected");
		gui.loginSuccessful(username);
		gg.setUser(username);

		Pair<String,String> p = MyIni.get(username);
		if (p!=null)
			connectToGoogle(p.getK(),SimpleEncryption.decrypt(p.getV()));
	}

	public void connectToGoogle(final String email, final String pass) {
		Thread t = new Thread() {
			public void run() {
				if (!gcom.login(email, pass)) { 
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							Mediator.this.displayError("Authentication Error");
							Mediator.this.gg.setMail("(none)");
						}
					});
					return;
				} else
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							Mediator.this.gg.setMail(email);
							MyIni.put(username, email, SimpleEncryption.encrypt(pass));
							Mediator.this.email = email;
						}
					});
			}
		};
		t.start();
	}

	public void logOut() {
		Log.debug("Cient " + username + " disconnected");
		comm.send(new LogOutMessage(username));
		gui.logOut();
		gg.logOut();
		tabs.removeAllElements();
		username = "(null)";
		gg.setMail("(none)");
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
		GroupTab t = getGroupTab(o);
		t.popOthers(o);
		getCurrentTab().setState(((ToolbarButton)o).getType()); 
	}


	public void loginError() {
		gui.error("Invalid credentials");
		gui.resetLogin();
	}

	public void loginError(String s) {
		gui.logOut();
		gui.error(s);
		Log.error(s);
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

		comm.send(new ProbeGroupMessage(username,group.toString()));
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
		gg.closeTab((String)group.getUserObject());
		tabs.remove(getTab(group.getUserObject().toString()));
		comm.send(new LeaveGroupMessage((String) group.getUserObject(), username));
	}

	public void joinGroupCommand(String user, String group, Color c) {
		if (!groupExists(group)) {
			if (email != null) {
				Log.debug("Create group " + group);
				gcom.createNew(group);
			} else {
				Log.debug("Email null");
			}
			comm.send(new NewGroupMessage(group, username, c));
		} else {
			if (userInGroup(user, group))
				return;
			comm.send(new JoinGroupMessage(group.toString(), username, c, email));
		}
	}

	public void joinGroupAccepted(String user, String group, Color color) {
		if (!user.equals(username)) {
			return;
		}
		GroupTab tb = gg.addTab(group,new DefaultListModel());
		Tab currentTab = new Tab(group,this);
		currentTab.setColor(color);
		currentTab.setCanvas(tb.panel);
		currentTab.setGroupTab(tb);

		currentTab.setDrawings(new Vector<Drawing>());
		tb.setDocument(new DefaultStyledDocument());
		tabs.add(currentTab);

		comm.send(new GetGroupLegend(group));
		comm.send(new GetGroupDrawings(group));
		comm.send(new GetGroupHistory(group));

		comm.send(new C2SEmailMessage(email, group));
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
		String tabContent = gcom.getContent(getCurrentTab().getName());

		if (tabContent.length() <= 0) {
			Log.debug("Empty content");
			return;
		}
		XMLParser p = new XMLParser(tabContent);
		Vector<Drawing> d = p.getDrawings();
		BufferedImage img = new BufferedImage(700, 500, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img.createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, img.getWidth(), img.getHeight());
		for (Drawing dr : d) {
			dr.draw(g2);
		}
		try {
			File file = new File(getCurrentTab().getName() + ".png");
			ImageIO.write(img, "png", file);
		} catch (Exception e) {
			Log.error(e.toString());
		}

	}

	public void setUserList(DefaultListModel model) {
		gg.setListModel(model);
	}

	public void setTreeList(DefaultTreeModel model) {
		gg.setTreeModel(model);
		treeModel = model;
	}

	public void setUserLegend(String groupName, DefaultListModel model) {
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
		final Tab t = getCurrentTab();
		d.setColor(t.color);
		t.delDrawing(d);
		t.addXMLDrawing(d);
		final String xmlrep = t.getXMLString();
		t.removeLastXML();
		Thread th = new Thread() {
			public void run() {
				gcom.addData(t.getName(), xmlrep);
			}
		};
		th.start();

		comm.send(new DrawingMessage(username, t.getName(), d));
	}

	public void updateDrawings(String group, Drawing d) {
		Tab t = getTab(group);
		if (t!=null) {
			t.addDrawing(d);
			t.addXMLDrawing(d);
		}
	}

	public void openGLogin() {
		new GoogleLogin(this);
	}

	public boolean gLogin(String user, String pass) {
		connectToGoogle(user, pass);
		return true; 
	}

	public void emailReceived(String email2, String group) {
		if (!userInGroup(username, group))
			return;
		gcom.addWriter(email2, group);
	}

	public void openTimeline() {
		Thread t = new Thread() {
			@Override
			public void run() {
				new Timeline(gcom, getCurrentTab().getName());
			}
		};
		t.start();
	}
}

