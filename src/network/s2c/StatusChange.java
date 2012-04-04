package network.s2c;

import app.Mediator;
import network.S2CMessage;

public class StatusChange extends S2CMessage {
	private static final long serialVersionUID = -3558083632977837640L;

	@Override
	public void execute(Mediator m) {
		System.out.println("Status change");
	}

}
