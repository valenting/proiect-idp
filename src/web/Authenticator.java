package web;

import app.Mediator;

/**
 * Class used to authenticate a client
 */
public class Authenticator {
	Mediator med;
	public Authenticator(Mediator med) {
		this.med = med;
	}
	
	public boolean authenticate(String user, String password) {
		// TODO connect to network by mediator and check if authenticated
		if (user.equals("user") && password.equals("pass"))
			return true;
		if (user.equals("me") && password.equals("test"))
			return true;
		return false;
	}
}
