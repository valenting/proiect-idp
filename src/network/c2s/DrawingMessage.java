package network.c2s;

import gui.drawings.Drawing;

import java.nio.channels.SelectionKey;

import server.ServerMediator;
import network.C2SMessage;

public class DrawingMessage extends C2SMessage {

	Drawing d;
	String username;
	String group;
	public DrawingMessage(String username, String group, Drawing d) {
		this.username = username;
		this.group = group;
		this.d = d;
	}
	
	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		m.addDrawing(username, group, d);
	}

}
