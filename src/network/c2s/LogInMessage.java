package network.c2s;

import java.nio.channels.SelectionKey;

import server.ServerMediator;

import network.C2SMessage;
import network.Message;

import app.Mediator;
import app.MediatorStub;

public class LogInMessage extends C2SMessage {
	String user;
	String pass;
	public LogInMessage(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}
	
	private static final long serialVersionUID = 4346500831789339892L;

	@Override
	public String toString() {
		return "LogInMessage";
	}

	@Override
	public void execute(ServerMediator m, SelectionKey key) {
		m.login(key,user,pass);
	}
	 
}