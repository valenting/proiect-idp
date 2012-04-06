package network;

import app.Mediator;

public abstract class S2CMessage extends Message {
	private static final long serialVersionUID = 756808708706500197L;
	public abstract void execute(Mediator m);
}
