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
}

class Group {
	String name;
	String createdBy;
	Vector<String> users;
	Hashtable<String, Color> colors;
	private static Color[] available = { Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED };
	public Group(String name, String user) {
		this.name = name; 
		users = new Vector<String>();
		users.add(user);
		colors.put(user, Color.BLACK);
		createdBy = user;
	}

	void addUser(String user, Color c) {
		users.add(user);
		colors.put(user, c);
	}

	void delUser(String user) {
		users.remove(user);
	}

	String getName() {
		return name;
	}

	boolean userInGroup(String username) {
		if (users.contains(username))
			return true;
		return false;
	}
	
	Vector<Color> availableColors() {
		Vector<Color> v = new Vector<Color>();
		for (Color c: available) 
			v.add(c);
		v.removeAll(colors.values());
		return v;
	}
	
	
}
