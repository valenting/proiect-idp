package web;

import java.beans.PropertyChangeSupport;
import java.util.Vector;

/**
 * Class used to communicate with the server and other clients
 */ 
public class Communicator {
	Vector<String> groups;
	public Communicator() {
		//super(null); // TODO
		groups = new Vector<String>();
	}
	
	public void createGroup() {
		groups.add("group"+groups.size());
	}
	
	

}
