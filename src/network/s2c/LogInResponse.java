package network.s2c;

import javax.swing.SwingUtilities;

import app.Mediator;
import network.S2CMessage;

public class LogInResponse extends S2CMessage {
	private static final long serialVersionUID = -3263390169240962237L;

	boolean valid;
	String username;
	public LogInResponse(String username, boolean valid) {
		this.valid = valid;
	}

	@Override
	public void execute(final Mediator m) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (valid)
					m.loginSuccessful();
				else
					m.loginError();
			}
		});


	}

}
