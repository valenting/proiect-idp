package network.c2s;

import java.nio.channels.SelectionKey;

import server.ServerMediator;
import network.C2SMessage;

public class ProbeGroupMessage extends C2SMessage {
	private static final long serialVersionUID = -2387853800486312570L;

	String user;
	String group;
	public ProbeGroupMessage(String user,String group) {
		this.user = user;
		this.group = group;
	}
	
	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		m.probeGroupDialog(k, user, group); 
	} 

}
