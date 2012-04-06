package network.s2c;



import java.awt.Color;
import javax.swing.SwingUtilities;

import app.Mediator;
import network.S2CMessage;

public class UpdateTextMessage extends S2CMessage {
	private static final long serialVersionUID = -8008968298420782563L;

	String group;
	String user;
	String text;
	int fontSize;
	Color fontColor;
	public UpdateTextMessage(String groupName, String userName, String text, int fontSize, Color fontColor) {
		this.group = groupName;
		this.user = userName;
		
		this.text = text;
		this.fontSize = fontSize;
		this.fontColor = fontColor;
	}
	
	@Override
	public void execute(final Mediator m) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				m.printText(group, user, text, fontSize, fontColor);
			}
		});
		
	}
	

}
