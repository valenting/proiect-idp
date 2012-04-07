package testing;

import java.awt.Color;

import server.GroupManager;
import junit.framework.TestCase;

public class GroupManagerTest extends TestCase {
	
	public GroupManagerTest(String name) {
		super(name);
	}
	
	public void testUserConnected() {
		GroupManager gm = new GroupManager();
		gm.connectUser("ceva");
		assertTrue(gm.userConnected("ceva"));
		assertFalse(gm.userConnected("ceva2"));
	}
	
	public void testAddGroup() {
		GroupManager gm = new GroupManager();
		gm.addGroup("ceva", "ceva", Color.black);
		assertTrue(gm.groupExists("ceva"));
		assertFalse(gm.groupExists("test"));
	}
	
	public void testLogOff() {
		GroupManager gm = new GroupManager();
		gm.connectUser("ceva");
		gm.logOffUser("ceva");
		assertFalse(gm.userConnected("ceva"));
	}
	
	public void testAddUser() {
		GroupManager gm = new GroupManager();
		gm.connectUser("ceva");
		gm.connectUser("ceva2");
		gm.addGroup("ceva", "ceva", Color.black);
		gm.addUserCommand("ceva2", "test", "ceva");
		assertFalse(gm.inGroup("ceva", "test"));
		assertEquals("User already in group", gm.addUserCommand("ceva", "ceva", "ceva"));
		gm.addUserCommand("ceva", "ceva2", "ceva");
		assertTrue(gm.inGroup("ceva", "ceva2"));
	}
	
	public void testJoinGroup() {
		GroupManager gm = new GroupManager();
		gm.connectUser("ceva");
		gm.connectUser("ceva2");
		gm.addGroup("ceva", "ceva", Color.black);
		// join la group care nu exista - false
		assertFalse(gm.joinGroupCommand("ceva", "test", Color.white));
		// join la un group care exista - true si nu face parte din grup
		assertTrue(gm.joinGroupCommand("ceva2", "ceva", Color.white));
		// join la grup daca e in grup deja - false
		assertFalse(gm.joinGroupCommand("ceva2", "ceva", Color.white));
	}
	
	public void testLeaveGroup() {
		GroupManager gm = new GroupManager();
		gm.connectUser("ceva");
		gm.connectUser("ceva2");
		gm.addGroup("ceva", "ceva", Color.black);
		gm.joinGroupCommand("ceva2", "ceva", Color.white);
		// grupul contine 2 useri
		gm.leaveGroupCommand("ceva", "ceva");
		// a mai ramas un user, cel ce a facut leave nu ar mai trebui sa fie in grup
		// dar grupul mai exista
		assertTrue(gm.groupExists("ceva"));
		assertFalse(gm.inGroup("ceva", "ceva"));
		gm.leaveGroupCommand("ceva2", "ceva");
		// ultimul a facut leave - grupul nu mai exista
		assertFalse(gm.inGroup("ceva", "ceva2"));
		assertFalse(gm.groupExists("ceva"));
	}

}
