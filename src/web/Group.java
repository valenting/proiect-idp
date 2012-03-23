package web;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultListModel;

import app.Pair;


public class Group {
	String name;
	String createdBy;
	Vector<String> users;
	Hashtable<String, Color> colors;
	private static Color[] available = { Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW };
	
	DefaultListModel userColorModel;
	
	public Group(String name, String user) {
		this.name = name; 
		users = new Vector<String>();
		colors = new Hashtable<String, Color>();
		users.add(user);
		colors.put(user, Color.BLACK);
		userColorModel = new DefaultListModel();
		// creator always has color black - convention
		userColorModel.addElement(new Pair<String,Color>(user,Color.black));
		createdBy = user;
		
	}

	public void addUser(String user, Color c) {
		users.add(user);
		colors.put(user, c);
		userColorModel.addElement(new Pair<String,Color>(user,c));
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
	
	public DefaultListModel getLegendModel() {
		return userColorModel;
	}
	
}