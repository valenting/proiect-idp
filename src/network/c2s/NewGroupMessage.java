package network.c2s;

import java.awt.Color;
import java.nio.channels.SelectionKey;

import server.ServerMediator;
import network.C2SMessage;

public class NewGroupMessage extends C2SMessage {
	private static final long serialVersionUID = -8008968298420782563L;

	String group;
	String user;
	Color c;
	public NewGroupMessage(String groupName, String userName, Color c) {
		group = groupName;
		user = userName;
		this.c = c;
	}
	
	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		m.newGroup(k, group, user,c);
	}

}
