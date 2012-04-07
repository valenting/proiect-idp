package network.c2s;

import java.nio.channels.SelectionKey;

import app.Log;

import server.ServerMediator;
import network.C2SMessage;

public class LogInMessage extends C2SMessage {
	String user;
	String pass;
	public LogInMessage(String user, String pass) {
		this.user = user;
		this.pass = pass;
		Log.debug(user);
	}
	
	private static final long serialVersionUID = 4346500831789339892L;

	@Override
	public String toString() {
		return "LogInMessage";
	}

	@Override
	public void execute(ServerMediator m, SelectionKey key) {
		Log.debug(user);
		m.login(key,user,pass);
	}
	 
}