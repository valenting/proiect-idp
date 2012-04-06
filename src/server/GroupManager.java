package server;

import gui.GroupTab;
import gui.drawings.Drawing;

import java.awt.Color;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

public class GroupManager {
	Vector<Group> groups;
	Vector<String> users;

	DefaultListModel userModel;
	DefaultTreeModel groupModel;
	
	public GroupManager() {
		groups = new Vector<Group>();
		users = new Vector<String>();
		groupModel = new DefaultTreeModel(new DefaultMutableTreeNode("Groups"));
		userModel = new DefaultListModel();
	}

	public DefaultListModel getListModel() {
		return userModel;
	}

	public DefaultTreeModel getTreeModel() {
		return groupModel;
	}

	public void connectUser(String user) {
		users.add(user);
		userModel.addElement(user);
	}

	public DefaultListModel getGroupLegend(String group) {
		Group g = getGroup(group);
		System.err.println("Grp: "+g);
		if (g!=null)
			return g.getLegendModel();
		else 
			return null;
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
	
	public void addDrawing(String user, String group, Drawing d) {
		Group g = getGroup(group);
		Color c = getColor(user, group);
		d.setColor(c);
		g.addDrawing(d); 
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

	public boolean joinGroupCommand(String username, String group, Color c) {
		Group g = getGroup(group);
		if (g!=null) 
			if (!g.userInGroup(username)) {
				g.addUser(username, c); 
				groupModel.insertNodeInto(new DefaultMutableTreeNode(username), getGroupNode(group), getGroupNode(group).getChildCount());
				return true;
			}
		return false;

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
	
	public String getFirstUser() {
		return users.firstElement();
	}
  
	public void addTextMessage(String groupName, String userName, String text,
			int fontSize, Color fontColor) {
		System.err.println(groupName);
		Group t = getGroup(groupName);
		if (t!=null)
			t.printText(userName, text, fontSize, fontColor);
	}
	
}

