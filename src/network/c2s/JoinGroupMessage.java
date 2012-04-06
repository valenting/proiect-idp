package network.c2s;

import java.awt.Color;
import java.nio.channels.SelectionKey;

import server.ServerMediator;
import network.C2SMessage;

public class JoinGroupMessage extends C2SMessage {
	private static final long serialVersionUID = -2387853800486312570L;

	String user;
	String group;
	Color c;
	
	public JoinGroupMessage(String string, String username, Color c) {
		this.group = string;
		this.user = username;
		this.c = c;
	}

	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		m.joinGroup(k, group, user, c); 
		
	}

}