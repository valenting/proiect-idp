package network.c2s;

import java.nio.channels.SelectionKey;

import app.Log;

import server.ServerMediator;
import network.C2SMessage;

public class GetGroupDrawings extends C2SMessage {
	private static final long serialVersionUID = 1285479161924918656L;

	String groupName;
	public GetGroupDrawings(String groupName) {
		this.groupName = groupName;
		Log.debug(groupName);
	}
	
	@Override
	public void execute(ServerMediator m, SelectionKey k) {
		Log.debug(groupName);
		m.getGroupDrawings(k, groupName);
	}

}
