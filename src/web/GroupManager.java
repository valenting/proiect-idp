package web;

import java.awt.Color;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class GroupManager {
	Vector<Group> groups;
	Vector<String> users;
	public GroupManager() {
		groups = new Vector<Group>();
		users = new Vector<String>();
	}

	public void connectUser(String user) {
		users.add(user);
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

