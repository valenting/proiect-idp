package network.s2c;

import gui.drawings.Drawing;

import java.util.Vector;

import app.Mediator;
import network.S2CMessage;

public class UpdateDrawings extends S2CMessage {
	private static final long serialVersionUID = -4389660395272100035L;
	
	Vector<Drawing> drawings;
	String groupName;
	public UpdateDrawings(String groupName, Vector<Drawing> drawings) {
		this.groupName = groupName;
		this.drawings = drawings;
	}

	@Override
	public void execute(Mediator m) {
		m.setDrawings(groupName, drawings);
	}

}
