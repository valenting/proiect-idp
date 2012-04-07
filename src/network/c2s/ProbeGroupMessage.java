package network.c2s;

import java.nio.channels.SelectionKey;

import app.Log;

import server.ServerMediator;
import network.C2SMessage;

public class ProbeGroupMessage extends C2SMessage {
	private static final long serialVersionUID = -2387853800486312570L;

	String user;
	String group;
	public ProbeGroupMessage(String user,String group) {
		this.user = user;
		this.group = group;
		Log.debug(group + "  " + user);
	}
	
	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		Log.debug(group + "  " + user);
		m.probeGroupDialog(k, user, group); 
	} 

}
