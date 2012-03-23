package web;

import java.util.Hashtable;

import app.Mediator;

/**
 * Class used to authenticate a client
 */
public class Authenticator {
	Mediator med;
	Hashtable<String, String> passwords;
	
	public Authenticator(Mediator med) {
		this.med = med;
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
