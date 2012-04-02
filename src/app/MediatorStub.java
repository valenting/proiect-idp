package app;

import java.nio.channels.SelectionKey;

import network.Message;

public abstract class MediatorStub {

	public abstract void send(SelectionKey key, Message m);
	
}
