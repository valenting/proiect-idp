package network.s2c;

import javax.swing.SwingUtilities;

import gui.drawings.Drawing;
import app.Mediator;
import network.S2CMessage;

public class DrawingMessage extends S2CMessage {
	private static final long serialVersionUID = -8779829819722389998L;
	
	String group;
	Drawing d;
	public DrawingMessage(String group, Drawing d) {
		this.d = d;
		this.group = group;
	}
	
	@Override
	public void execute(final Mediator m) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				m.updateDrawings(group,d);
			}
		});
		
	}

}
