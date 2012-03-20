package web;

import java.awt.Color;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
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
		userModel = new DefaultListModel();
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

	public void disconnectUser(String user) {
		users.remove(user);
	}

	public Vector<String> getConnectedUsers() {
		return users;
	}

	public Vector<Group> getGroups() {
		return groups;
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

	public boolean addUser(String group, String username, Color c) {
		for (Group g : groups)
			if (g.getName().equals(group)) {
				g.addUser(username, c);
				return true;
			}
		return false;
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

}

