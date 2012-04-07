package network.c2s;

import java.nio.channels.SelectionKey;

import app.Log;


import server.ServerMediator;
import network.C2SMessage;

public class AddUserMessage extends C2SMessage {
	private static final long serialVersionUID = -8008968298420782563L;

	String group;
	String user;
	String addedUser;
	public AddUserMessage(String groupName, String userName, String addedUser) {
		group = groupName;
		user = userName;
		this.addedUser = addedUser;
		Log.debug(addedUser + " to " + group + " by " + user);
	}
	
	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		Log.debug(addedUser + " to " + group + " by " + user);
		m.addUser(k, group, user, addedUser);
	}

}
