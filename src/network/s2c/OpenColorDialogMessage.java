package network.s2c;

import java.awt.Color;
import java.util.Vector;

import app.Mediator;
import network.S2CMessage;

public class OpenColorDialogMessage extends S2CMessage {
	private static final long serialVersionUID = -5707965021672012334L;
	Vector<Color> cols;
	String group;
	public OpenColorDialogMessage(Vector<Color> availableColors, String group) {
		cols = availableColors;
		this.group = group;
	}

	@Override
	public void execute(Mediator m) {
		m.openColorChooser(cols, group);
	}

}
