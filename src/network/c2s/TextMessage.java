package network.c2s;

import java.awt.Color;
import java.nio.channels.SelectionKey;

import server.ServerMediator;
import network.C2SMessage;

public class TextMessage extends C2SMessage {
	private static final long serialVersionUID = -8008968298420782563L;

	String group;
	String user;
	String text;
	int fontSize;
	Color fontColor;
	public TextMessage(String groupName, String userName, String text, int fontSize, Color fontColor) {
		this.group = groupName;
		this.user = userName;
		
		this.text = text;
		this.fontSize = fontSize;
		this.fontColor = fontColor;
	}
	
	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		m.addTextMessage(group, user, text, fontSize, fontColor);
	}

}
