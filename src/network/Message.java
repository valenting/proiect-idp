package network;

import java.io.Serializable;
import java.nio.channels.SelectionKey;

import app.Mediator;
import app.MediatorStub;

public abstract class Message implements Serializable {
	private static final long serialVersionUID = -7784455849848939000L;
	abstract public void execute(MediatorStub m, SelectionKey key);
}
	
