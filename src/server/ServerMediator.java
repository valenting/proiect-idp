package server;

import java.io.IOException;
import java.nio.channels.SelectionKey;

import network.Message;
import app.Mediator;
import app.MediatorStub;

public class ServerMediator extends MediatorStub {
	Server serv;
	SAuthenticator sauth;
	public ServerMediator(Server s) {
		serv = s;
		sauth = new SAuthenticator();
	}
	
	public void send(SelectionKey key, Message m) {
		serv.write(key, m);
	}
	
	public void authenticate(SelectionKey key, String user, String pass) {
		boolean valid = sauth.authenticate(user, pass);
		if (valid) {
			// TODO send new message with confirmation
		} else {
			// TODO send new message with rejection
		}
	}
}
