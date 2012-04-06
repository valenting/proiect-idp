package network.s2c;



import java.awt.Color;
import java.nio.channels.SelectionKey;

import app.Mediator;

import server.ServerMediator;
import network.C2SMessage;
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
	public void execute(Mediator m) {
		m.printText(group, user, text, fontSize, fontColor);
	}
	

}
