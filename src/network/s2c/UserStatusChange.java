package network.s2c;

import javax.swing.DefaultListModel;

import app.Mediator;
import network.S2CMessage;

public class UserStatusChange extends S2CMessage {
	private static final long serialVersionUID = -3558083632977837640L;
	DefaultListModel model;
	public UserStatusChange(DefaultListModel model) {
		this.model = model;
	}
	
	@Override
	public void execute(Mediator m) {
		System.out.println("Status change");
		m.setUserList(model); // TODO Gui Thread
	}

}
