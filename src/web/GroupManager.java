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
		userModel.addElement("phony");
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
	
/*
	public boolean addUser(String group, String username, Color c) {
		for (Group g : groups)
			if (g.getName().equals(group)) {
				g.addUser(username, c);
				// TODO add to group Model
				return true;
			}
		return false;
	}
*/
	
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

	public void addUserCommand(String username, String addedUser, String group) {
		Group g = getGroup(group);
		System.out.println("adding user:"+addedUser+" to:"+group);
		if (g!=null && username!=null && addedUser!=null && g.userInGroup(username) && !g.userInGroup(addedUser)) {
			g.addUser(addedUser, Color.CYAN); // TODO choose color
			groupModel.insertNodeInto(new DefaultMutableTreeNode(addedUser), getGroupNode(group), getGroupNode(group).getChildCount());
		}
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
	
	
	
	/*** STUBS ***/
	
	public void connectedUserEvent() {
		
	}
	
	public void disconnectedUserEvent() {
		
	}
	
	public void joinGroupEvent() {
		
	}

	public void leaveGroupEvent() {
		
	}
}

