package network;

import java.nio.channels.SelectionKey;

import app.Mediator;
import app.MediatorStub;

public class LogInMessage extends Message {
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
	public void execute(MediatorStub m, SelectionKey key) {
		m.send(key, this);
		
	}
	
}