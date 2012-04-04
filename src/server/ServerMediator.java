package server;

import java.io.IOException;
import java.nio.channels.SelectionKey;

import network.Message;
import network.s2c.LogInResponse;
import network.s2c.StatusChange;
import app.Mediator;
import app.MediatorStub;

public class ServerMediator extends MediatorStub {
	Server serv;
	SAuthenticator sauth;
	GroupManager gm;
	public ServerMediator(Server s) {
		serv = s;
		sauth = new SAuthenticator();
		gm = new GroupManager();
	}
	
	public void send(SelectionKey key, Message m) {
		serv.write(key, m);
	}
	
	public void login(SelectionKey key, String user, String pass) {
		boolean valid = sauth.authenticate(user, pass); 
		serv.write(key,new LogInResponse(valid));
		if (valid) {
			gm.connectUser(user);
			serv.broadcast(new StatusChange());
		}
	}
	
	public void logOff(SelectionKey key, String user) {
		gm.logOffUser(user);
		serv.broadcast(new StatusChange());
	}
	
	
}
