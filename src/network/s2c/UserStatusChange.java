package network.s2c;

import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

import app.Mediator;
import network.S2CMessage;

public class UserStatusChange extends S2CMessage {
	private static final long serialVersionUID = -3558083632977837640L;
	DefaultListModel model;
	public UserStatusChange(DefaultListModel model) {
		this.model = model;
	}
	
	@Override
	public void execute(final Mediator m) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				m.setUserList(model);
			}
		});
	}

}
