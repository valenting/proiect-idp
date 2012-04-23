package network.s2c;

import java.awt.Color;

import javax.swing.SwingUtilities;

import app.Mediator;
import network.S2CMessage;

public class OpenPanelMessage extends S2CMessage {
	private static final long serialVersionUID = -3586406295549909891L;

	String user;
	String group;
	Color color;
	public OpenPanelMessage(String username, String group, Color color) {
		this.user = username;
		this.group = group;
		this.color = color;
	}
	
	@Override
	public void execute(final Mediator m) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				m.joinGroupAccepted(user, group, color);
			}
		});
		
	}

}
