package web;

import gui.drawings.Drawing;

import java.awt.Color;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

import app.Mediator;

public class GroupManager {
	Vector<Group> groups;
	Vector<String> users;

	DefaultListModel userModel;
	DefaultTreeModel groupModel;
	Mediator med;
	public GroupManager(Mediator med) {
		this.med = med;
		groups = new Vector<Group>();
		users = new Vector<String>();
		userModel = new DefaultListModel();
		userModel.addElement("phony");
		userModel.addElement("someone");

		groupModel = new DefaultTreeModel(new DefaultMutableTreeNode("Groups"));
	}

	public ListModel getListModel() {
		return userModel;
	}

	public TreeModel getTreeModel() {
		return groupModel;
	}

	public void connectUser(String user) {
		users.add(user);
		userModel.addElement(user);
	}


	public Vector<String> getConnectedUsers() {
		return users;
	}

	public DefaultListModel getGroupLegend(String group) {
		Group g = getGroup(group);
		return g.getLegendModel();
	}

	public boolean addGroup(String group, String username) {
		for (Group g : groups)
			if (g.getName().equals(group))
				return false;
		groups.add(new Group(group,username));
		DefaultMutableTreeNode t = new DefaultMutableTreeNode(group);
		groupModel.insertNodeInto(t, (MutableTreeNode) groupModel.getRoot(), ((DefaultMutableTreeNode)groupModel.getRoot()).getChildCount());
		groupModel.insertNodeInto(new DefaultMutableTreeNode(username), t, 0);

		return true;
	}

	public boolean logOffUser(String username) {
		for (Group g : groups) {
			g.delUser(username);
		}
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)groupModel.getRoot();
		for (int i=0;i<userModel.getSize();i++)
			if (userModel.get(i).toString().equals(username)) {
				userModel.remove(i);
				break;
			}
		for (int i=0;i<root.getChildCount();i++) {
			DefaultMutableTreeNode n = (DefaultMutableTreeNode) root.getChildAt(i);
			for (int j=0;j<n.getChildCount();j++) {
				DefaultMutableTreeNode t = (DefaultMutableTreeNode) n.getChildAt(j);
				if (t.getUserObject().equals(username)) {
					groupModel.removeNodeFromParent(t);
					break;
				}
			}
			if (n.getChildCount()==0) {
				groupModel.removeNodeFromParent(n);
				groups.remove(this.getGroup((String) n.getUserObject()));
				i--;
			}
		}

		return true;
	}


	public boolean inGroup(String group, String username) {
		for (Group g : groups)
			if (g.getName().equals(group)) 
				return g.userInGroup(username);
		return false;
	}

	public boolean groupExists(String group) {
		for (Group g: groups)
			if (g.getName().equals(group))
				return true;
		return false;
	}

	private DefaultMutableTreeNode getGroupNode(String name) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)groupModel.getRoot();
		for (int i=0;i<root.getChildCount();i++) {
			DefaultMutableTreeNode n = (DefaultMutableTreeNode) root.getChildAt(i);
			if (n.getUserObject().equals(name))
				return n;
		}
		return null;
	}

	private DefaultMutableTreeNode getUserNode(String name, String group) {
		DefaultMutableTreeNode n = getGroupNode(group);
		if (n==null)
			return null;

		for (int j=0;j<n.getChildCount();j++) {
			DefaultMutableTreeNode t = (DefaultMutableTreeNode) n.getChildAt(j);
			if (t.getUserObject().equals(name))
				return t;
		}

		return null;
	}

	private Group getGroup(String name) {
		for (Group g : groups)
			if (g.getName().equals(name))
				return g;
		return null; 
	}

	public Vector<Color> getAvailableColors(String group) {
		return getGroup(group).availableColors();
	}

	/*** COMMANDS ***/

	public String addUserCommand(String username, String addedUser, String group) {
		Group g = getGroup(group);

		if (!g.userInGroup(username))
			return "You are not allowed to add users. Try JOIN";

		if (g.userInGroup(addedUser))
			return "User already in group";

		if (g!=null && username!=null && addedUser!=null && g.userInGroup(username) && !g.userInGroup(addedUser)) {
			try {
				Color c = getAvailableColors(group).firstElement();
				g.addUser(addedUser, c);
				groupModel.insertNodeInto(new DefaultMutableTreeNode(addedUser), getGroupNode(group), getGroupNode(group).getChildCount());
			} catch (Exception e) {
				return "No more colors available";
			}	
		}
		return null;
	}

	public void joinGroupCommand(String username, String group, Color c) {
		Group g = getGroup(group);
		if (g!=null) 
			if (!g.userInGroup(username)) {
				g.addUser(username, c); 
				groupModel.insertNodeInto(new DefaultMutableTreeNode(username), getGroupNode(group), getGroupNode(group).getChildCount());
			}

	}

	public void leaveGroupCommand(String username, String group) {
		Group g = getGroup(group);
		if (g!=null)
			if (g.userInGroup(username)) {
				g.delUser(username);
				DefaultMutableTreeNode p = (DefaultMutableTreeNode) getUserNode(username, group).getParent();
				groupModel.removeNodeFromParent(getUserNode(username, group));
				if (p.isLeaf())  { // TODO Stage2: do this for all leave group events  
					groupModel.removeNodeFromParent(p);
					groups.remove(this.getGroup((String) p.getUserObject()));
				}
			}
	}

	public Color getColor(String username, String group) {
		return getGroup(group).getUserColor(username);
	}

	public DefaultStyledDocument getDocument(String group) {
		return getGroup(group).getDocument();
	}

	public Vector<Drawing> getDrawings(String group) {
		return getGroup(group).getDrawings();
	}

	/*** STUBS ***/

	public void connectedUserEvent(String username) {
		connectUser(username);
	}

	public void disconnectedUserEvent(String username) {
		logOffUser(username);
	}

	public boolean createGroupEvent(String username,String group) {
		return addGroup(group, username);
	}
	
	public void joinGroupEvent(String username, String group) {
		try {
			Color c =getAvailableColors(group).firstElement();
			joinGroupCommand(username, group, c);
		} catch (Exception e) {

		}
	}
	
	public void addUserEvent(String by, String username, String group) {
		med.addUserEvent(username, group);
		if (!inGroup(group, username))
			addUserCommand(by, username, group);
	}

	public void leaveGroupEvent(String username, String group) {
		leaveGroupCommand(username, group);
	}
	
	public String getFirstUser() {
		return users.firstElement();
	}
}

