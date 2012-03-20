package web;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultListModel;


public class Group {
	String name;
	String createdBy;
	Vector<String> users;
	Hashtable<String, Color> colors;
	private static Color[] available = { Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED };
	
	
	
	DefaultListModel userColorModel; // TODO
	
	public Group(String name, String user) {
		this.name = name; 
		users = new Vector<String>();
		colors = new Hashtable<String, Color>();
		users.add(user);
		colors.put(user, Color.BLACK);
		createdBy = user;
	}

	public void addUser(String user, Color c) {
		users.add(user);
		colors.put(user, c);
	}

	public void delUser(String user) { 
		users.remove(user);
	}

	public String getName() {
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
	
	public Vector<String> users() {
		return users;
	}
	
}