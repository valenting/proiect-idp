package network.c2s;

import java.nio.channels.SelectionKey;

import server.ServerMediator;
import network.C2SMessage;

public class C2SEmailMessage extends C2SMessage {
	private static final long serialVersionUID = -2596814881179398270L;

	String email;
	String group;
	
	public C2SEmailMessage(String email, String group) {
		this.email = email;
		this.group = group;
	}
	
	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		m.receivedEmail(email, group);
	}
	

}
