package network.c2s;

import java.nio.channels.SelectionKey;

import server.ServerMediator;
import network.C2SMessage;

public class NewGroupMessage extends C2SMessage {
	private static final long serialVersionUID = -8008968298420782563L;

	String group;
	String user;
	
	public NewGroupMessage(String groupName, String userName) {
		group = groupName;
		user = userName;
	}
	
	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		m.newGroup(k, group, user);
	}

}
