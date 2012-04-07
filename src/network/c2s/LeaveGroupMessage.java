package network.c2s;

import java.nio.channels.SelectionKey;

import app.Log;

import server.ServerMediator;
import network.C2SMessage;

public class LeaveGroupMessage extends C2SMessage {
	private static final long serialVersionUID = 3618045789687100028L;

	String groupName;
	String userName;
	public LeaveGroupMessage(String group, String user) {
		this.groupName = group;
		this.userName = user;
		Log.debug(userName + " from "+ groupName);
	}
	
	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		Log.debug(userName + " from "+ groupName);
		m.leaveGroup(k, groupName, userName);
	}

}
