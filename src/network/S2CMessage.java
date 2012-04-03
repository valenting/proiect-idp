package network;

import java.nio.channels.SelectionKey;

import app.Mediator;
import app.MediatorStub;

public abstract class S2CMessage extends Message {
	private static final long serialVersionUID = 756808708706500197L;
	public abstract void execute(Mediator m);
}
