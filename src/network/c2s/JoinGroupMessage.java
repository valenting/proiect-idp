package network.c2s;

import java.awt.Color;
import java.nio.channels.SelectionKey;

import app.Log;

import server.ServerMediator;
import network.C2SMessage;

public class JoinGroupMessage extends C2SMessage {
	private static final long serialVersionUID = -2387853800486312570L;

	String user;
	String group;
	Color c;
	String email;
	
	public JoinGroupMessage(String string, String username, Color c, String email) {
		this.group = string;
		this.user = username;
		this.c = c;
		this.email = email;
		Log.debug(user + " to "+ group + " " + c);
	}

	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		Log.debug(user + " to "+ group + " " + c);
		m.joinGroup(k, group, user, c, email); 
		
	}

}
