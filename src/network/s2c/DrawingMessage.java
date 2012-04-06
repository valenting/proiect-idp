package network.s2c;

import gui.drawings.Drawing;
import app.Mediator;
import network.S2CMessage;

public class DrawingMessage extends S2CMessage {

	
	String group;
	Drawing d;
	public DrawingMessage(String group, Drawing d) {
		this.d = d;
		this.group = group;
	}
	
	@Override
	public void execute(Mediator m) {
		m.updateDrawings(group,d);
	}

}
