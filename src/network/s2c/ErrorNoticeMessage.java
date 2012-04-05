package network.s2c;

import javax.swing.SwingUtilities;

import app.Mediator;
import network.S2CMessage;

public class ErrorNoticeMessage extends S2CMessage {

	private static final long serialVersionUID = 1889648042453696799L;
	String errorNotice;
	public ErrorNoticeMessage(String s) {
		this.errorNotice = s;
	}
	
	@Override
	public void execute(final Mediator m) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				 m.displayError(errorNotice);
			}
		});
	}

}
