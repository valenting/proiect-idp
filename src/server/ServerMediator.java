package server;

import java.awt.Color;
import java.io.IOException;
import java.nio.channels.SelectionKey;

import network.Message;
import network.s2c.ErrorNoticeMessage;
import network.s2c.LogInResponse;
import network.s2c.OpenColorDialogMessage;
import network.s2c.OpenPanelMessage;
import network.s2c.StatusChange;
import network.s2c.TreeStatusChange;
import network.s2c.UserStatusChange;
import app.Mediator;
import app.MediatorStub;

public class ServerMediator {
	Server serv;
	SAuthenticator sauth;
	GroupManager gm;
	public ServerMediator(Server s) {
		serv = s;
		sauth = new SAuthenticator();
		gm = new GroupManager();
	}

	public void login(SelectionKey key, String user, String pass) {
		boolean valid = sauth.authenticate(user, pass); 
		serv.write(key,new LogInResponse(valid));
		if (valid) {
			gm.connectUser(user);
			serv.broadcast(new UserStatusChange(gm.getListModel()));
		}
	}

	public void logOff(SelectionKey key, String user) {
		gm.logOffUser(user);
		System.out.println("LogOff "+user);
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
			serv.write(key, new OpenPanelMessage(userName,groupName)); // ACK
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




}
