package server;

import java.util.Hashtable;

import app.Mediator;

/**
 * Class used to authenticate a client
 */
public class SAuthenticator {
	Hashtable<String, String> passwords;
	
	public SAuthenticator() {
		passwords = new Hashtable<String, String>();
		passwords.put("user", "pass");
		passwords.put("me","test");
		passwords.put("bla","mypass");
		passwords.put("qwe","rty");
	}
	
	public boolean authenticate(String user, String password) {
		String ps = passwords.get(user);
		if (password.equals(ps))
			return true;
		return false;
	}
}
