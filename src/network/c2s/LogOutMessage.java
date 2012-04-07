package network.c2s;

import java.nio.channels.SelectionKey;

import app.Log;
import server.ServerMediator;
import network.C2SMessage;

public class LogOutMessage extends C2SMessage {

	private static final long serialVersionUID = 4346500831789339892L;
	String user;
	public LogOutMessage(String user) {
		this.user = user;
		Log.debug(user);
	}
	
	public String toString() {
		return "LogOutMessage";
	}

	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		Log.debug(user);
		m.logOff(k, user);
	}

	
}