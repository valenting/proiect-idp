package server;

import java.io.IOException;
import java.nio.channels.SelectionKey;

import network.Message;
import app.Mediator;
import app.MediatorStub;

public class ServerMediator extends MediatorStub {
	Server serv;
	public ServerMediator(Server s) {
		serv = s;
	}
	
	public void send(SelectionKey key, Message m) {
		try {
			serv.write(key, m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
