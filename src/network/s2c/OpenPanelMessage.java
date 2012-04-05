package network.s2c;

import java.awt.Color;

import app.Mediator;
import network.S2CMessage;

public class OpenPanelMessage extends S2CMessage {
	private static final long serialVersionUID = -3586406295549909891L;

	String user;
	String group;
	public OpenPanelMessage(String username, String group) {
		this.user = username;
		this.group = group;
	}
	
	@Override
	public void execute(Mediator m) {
		m.joinGroupAccepted(user, group);
	}

}
