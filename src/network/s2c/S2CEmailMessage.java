package network.s2c;

import javax.swing.SwingUtilities;

import app.Mediator;
import network.S2CMessage;

public class S2CEmailMessage extends S2CMessage{
	private static final long serialVersionUID = -5551441249779803147L;

	String group;
	String email;
	public S2CEmailMessage(String email, String group) {
		this.email = email;
		this.group = group;
	}
	@Override
	public void execute(final Mediator m) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				m.emailReceived(email, group);
			}
		});
	}
	
}
