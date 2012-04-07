package testing;

import server.SAuthenticator;
import junit.framework.TestCase;

public class SAuthenticatorTest extends TestCase {
	// Clasa ce testeaza ca autentificare functioneaza cum trebuie
	// Adica, daca se verifica bine userul si parola
	
	public SAuthenticatorTest(String name) {
		super(name);
	}
	
	public void testCorect() {
		int counter = 0;
		SAuthenticator sa = new SAuthenticator();
		// testam ca pe toate le vede ok
		if (sa.authenticate("user", "pass"))
			counter++;
		if (sa.authenticate("me", "test"))
			counter++;
		if (sa.authenticate("bla", "mypass"))
			counter++;
		if (sa.authenticate("qwe", "rty"))
			counter++;
		if (sa.authenticate("emma", "emma"))
			counter++;
		if (sa.authenticate("vali", "vali"))
			counter++;
		if (sa.authenticate("test", "test"))
			counter++;
		if (sa.authenticate("unu", "unu"))
			counter++;
		assertEquals(8, counter);
	}
	
	public void testWrongUser() {
		SAuthenticator sa = new SAuthenticator();
		// Testam ca un user gresit nu se poate autentifica
		assertFalse(sa.authenticate("ceva", ""));
	}
	
	public void testNullUser() {
		SAuthenticator sa = new SAuthenticator();
		// Testam ca un user null nu se poate autentifica
		assertFalse(sa.authenticate(null, ""));
	}
	
	public void testWrongPass() {
		SAuthenticator sa = new SAuthenticator();
		// Testam ca un user nu se poate autentifica, cu parola gresita
		assertFalse(sa.authenticate("test", ""));
	}
	
	public void testNullPass() {
		SAuthenticator sa = new SAuthenticator();
		// Testam ca un user nu se poate autentifica, cu parola gresita
		assertFalse(sa.authenticate("test", null));
	}
}
