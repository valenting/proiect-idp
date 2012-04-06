package server;

import gui.drawings.Drawing;

import java.awt.Color;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Hashtable;


import network.Message;
import network.s2c.DrawingMessage;
import network.s2c.ErrorNoticeMessage;
import network.s2c.LogInResponse;
import network.s2c.OpenColorDialogMessage;
import network.s2c.OpenPanelMessage;
import network.s2c.StatusChange;
import network.s2c.TreeStatusChange;
import network.s2c.UpdateDrawings;
import network.s2c.UpdateHistory;
import network.s2c.UpdateLegend;
import network.s2c.UpdateTextMessage;
import network.s2c.UserStatusChange;
import app.Mediator;
import app.MediatorStub;

public class ServerMediator {
	Server serv;
	SAuthenticator sauth;
	GroupManager gm;
	Hashtable<SocketChannel,String> hash;
	public ServerMediator(Server s) {
		serv = s;
		sauth = new SAuthenticator();
		gm = new GroupManager();
		hash = new Hashtable<SocketChannel, String>();
	}

	public void login(SelectionKey key, String user, String pass) {
		boolean valid = sauth.authenticate(user, pass); 
		serv.write(key,new LogInResponse(valid));
		if (valid) {
			gm.connectUser(user);
			serv.broadcast(new UserStatusChange(gm.getListModel()));
			serv.write(key, new UserStatusChange(gm.getListModel()));
			serv.write(key, new TreeStatusChange(gm.getTreeModel()));
			hash.put((SocketChannel) key.channel(), user);
		}
	}

	public void disconnectUser(SocketChannel chan) {
		this.logOff(null, hash.get(chan));
	}
	
	public void logOff(SelectionKey key, String user) {
		gm.logOffUser(user);
		serv.broadcast(new UserStatusChange(gm.getListModel()));
		serv.broadcast(new TreeStatusChange(gm.getTreeModel()));
	}

	public void newGroup(SelectionKey key, String groupName, String userName) {
		gm.addGroup(groupName, userName); 
		serv.broadcast(new TreeStatusChange(gm.getTreeModel()));
		serv.write(key, new OpenPanelMessage(userName,groupName));
	}

	public void joinGroup(SelectionKey key, String groupName, String userName, Color c) {
		if (gm.joinGroupCommand(userName, groupName, c)) {
			serv.broadcast(new TreeStatusChange(gm.getTreeModel()));
			serv.write(key, new OpenPanelMessage(userName,groupName));
			serv.broadcast(new UpdateLegend(groupName, gm.getGroupLegend(groupName)));
		} else
			serv.write(key, new ErrorNoticeMessage("Could not join group"));
	}

	public void leaveGroup(SelectionKey key, String groupName, String userName) {
		gm.leaveGroupCommand(userName, groupName);
		serv.broadcast(new TreeStatusChange(gm.getTreeModel()));
	}

	public void addUser(SelectionKey key, String groupName, String userName, String by) {
		String s = gm.addUserCommand(by, userName, groupName);
		if (s==null) {
			serv.broadcast(new TreeStatusChange(gm.getTreeModel()));
			serv.broadcast(new OpenPanelMessage(userName,groupName));
		} else
			serv.write(key, new ErrorNoticeMessage(s));
	}

	public void getColorDialog(SelectionKey k, String group) {
		serv.write(k, new OpenColorDialogMessage(gm.getAvailableColors(group),group));
	}

	public void getGroupHistory(SelectionKey k, String groupName) {
		serv.write(k, new UpdateHistory(groupName, gm.getDocument(groupName)));
	} 
	
	public void getGroupLegend(SelectionKey k, String groupName) {
		System.err.println("getGroup");
		serv.write(k, new UpdateLegend(groupName, gm.getGroupLegend(groupName)));
	}
	
	public void getGroupDrawings(SelectionKey k, String groupName) {
		serv.write(k, new UpdateDrawings(groupName, gm.getDrawings(groupName)));
	}

	public void addDrawing(String username, String group, Drawing d) {
		gm.addDrawing(username, group, d);
		serv.broadcast(new DrawingMessage(group, d));
	}
	
	public void addTextMessage(String groupName, String userName, String text, int fontSize, Color fontColor) {
		gm.addTextMessage(groupName, userName, text, fontSize,fontColor);
		serv.broadcast(new UpdateTextMessage(groupName, userName, text, fontSize, fontColor));
	} 
	

 
}
