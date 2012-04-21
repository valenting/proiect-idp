package web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentEntry;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.util.AuthenticationException;

import app.Mediator;

public class GoogleComm {
	private DocsService service;
	private String host = "docs.google.com";
	private final String URL_DOCUMENT = "/document";
	
	public GoogleComm(Mediator m) {
	
		service = new DocsService("Whiteboard");
	}
	
	public void login(String user, String pass) throws AuthenticationException  {
		if (user == null || pass == null)
			throw new AuthenticationException("Null login credentials");
		service.setUserCredentials(user,pass);
	}
	
	public DocumentListEntry createNew(String title) throws Exception {
		if (title == null)
			throw new Exception("Null title for document");
		DocumentListEntry newEntry = new DocumentEntry();
		newEntry.setTitle(new PlainTextConstruct(title));
		return service.insert(buildUrl("", null), newEntry);
	}
	
	private URL buildUrl(String path, Map<String, String> param) throws Exception {
		StringBuffer url = new StringBuffer();
		if (path == null)
			throw new Exception("Null Path");
		url.append("https://" + host + URL_DOCUMENT + path);
		if (param != null && param.size() > 0) {
			Set<Map.Entry<String, String>> params = param.entrySet();
			Iterator<Map.Entry<String, String>> itr = params.iterator();
			url.append("?");
			while (itr.hasNext()) {
				Map.Entry<String, String> entry = itr.next();
		        url.append(entry.getKey() + "=" + entry.getValue());
		        if (itr.hasNext()) {
		          url.append("&");
		        }
			}
		}
		return new URL(url.toString());
	}
	
	public static void main(String args[]) {
		GoogleComm comm = new GoogleComm(null);
		
	}
}
