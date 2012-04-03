package network.c2s;

import java.nio.channels.SelectionKey;

import server.ServerMediator;

import network.C2SMessage;
import network.Message;

import app.Mediator;
import app.MediatorStub;

public class LogOutMessage extends C2SMessage {

	private static final long serialVersionUID = 4346500831789339892L;


	
	public String toString() {
		return "LogOutMessage";
	}



	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		// TODO Auto-generated method stub
		
	}

	
}