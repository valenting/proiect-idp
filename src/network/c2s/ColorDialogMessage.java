package network.c2s;

import java.awt.Color;
import java.nio.channels.SelectionKey;

import server.ServerMediator;
import network.C2SMessage;

public class ColorDialogMessage extends C2SMessage {
	private static final long serialVersionUID = -2387853800486312570L;

	String user;
	String group;
	public ColorDialogMessage(String group) {
		this.group = group;
	}
	
	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		m.getColorDialog(k, group); 
	} 

}
