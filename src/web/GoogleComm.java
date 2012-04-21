package web;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.util.AuthenticationException;

import app.Mediator;

public class GoogleComm {
	public DocsService service;
	public String host = "docs.google.com";
	
	public GoogleComm(Mediator m) {
	
		service = new DocsService("Whiteboard");
	}
	
	public void login(String user, String pass) throws AuthenticationException  {
		service.setUserCredentials(user,pass);
	}
	
	public static void main(String args[]) {
		GoogleComm comm = new GoogleComm(null);
		
	}
}
