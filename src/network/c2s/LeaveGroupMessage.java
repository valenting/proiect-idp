package network.c2s;

import java.nio.channels.SelectionKey;

import server.ServerMediator;
import network.C2SMessage;

public class LeaveGroupMessage extends C2SMessage {
	private static final long serialVersionUID = 3618045789687100028L;

	String groupName;
	String userName;
	public LeaveGroupMessage(String group, String user) {
		this.groupName = group;
		this.userName = user;
	}
	
	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		m.leaveGroup(k, groupName, userName);
	}

}
