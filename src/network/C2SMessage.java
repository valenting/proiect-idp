package network;

import java.nio.channels.SelectionKey;

import server.ServerMediator;

import app.MediatorStub;

public abstract class C2SMessage extends Message {
	private static final long serialVersionUID = 8037080082533740316L;
	public abstract void execute(ServerMediator m, SelectionKey k);
}
