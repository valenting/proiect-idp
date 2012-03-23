package worker;

import web.GroupManager;

public class Tester extends Thread {
	GroupManager man;
	public Tester(GroupManager man) {
		this.man = man;
	}
	
	public void run() {
		try {
			Thread.sleep(5000);
			man.connectedUserEvent("john");
			Thread.sleep(5000);
			man.createGroupEvent("john", "john's group");
			Thread.sleep(5000);
			man.addUserEvent("john", man.getFirstUser(), "john's group");
			Thread.sleep(5000);
			man.leaveGroupCommand("john", "john's group");
		} catch (Exception e) {
			System.err.println("Tester err");
		}
	}
}
