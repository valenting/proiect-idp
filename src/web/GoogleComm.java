package web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.ChangelogFeed;
import com.google.gdata.data.docs.DocumentEntry;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.extensions.LastModifiedBy;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.sun.jndi.toolkit.url.UrlUtil;

import app.Mediator;

public class GoogleComm {
	private DocsService service;
	private String host = "docs.google.com";
	private final String URL_DOCUMENT = "/document";

	public GoogleComm(Mediator m) {

		service = new DocsService("Whiteboard");
		service.setProtocolVersion(DocsService.Versions.V2);
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
		System.out.println(url.toString());
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

	public void showAllDocs() throws IOException, ServiceException {
		URL feedUri = new URL("https://docs.google.com/feeds/documents/private/full/");
		DocumentListFeed feed = service.getFeed(feedUri, DocumentListFeed.class);

		for (DocumentListEntry entry : feed.getEntries()) {
			printDocumentEntry(entry);
		} 
	}

	public void printDocumentEntry(DocumentListEntry doc) {
		String resourceId = doc.getResourceId();
		String docType = resourceId.substring(0, resourceId.lastIndexOf(':'));

		System.out.println("'" + doc.getTitle().getPlainText() + "' (" + docType + ")");
		System.out.println("  link to Google Docs: " + doc.getHtmlLink().getHref());
		System.out.println("  resource id: " + resourceId);

		// print the parent folder the document is in
		if (!doc.getFolders().isEmpty()) {
			System.out.println("  in folder: " +  doc.getFolders());
		}

		// print the timestamp the document was last viewed
		DateTime lastViewed = doc.getLastViewed();
		if (lastViewed != null) {
			System.out.println("  last viewed: " + lastViewed.toString());
		}

		// print who made that modification
		LastModifiedBy lastModifiedBy = doc.getLastModifiedBy();
		if (lastModifiedBy != null) {
			System.out.println("  updated by: " +
					lastModifiedBy.getName() + " - " + lastModifiedBy.getEmail());
		}

		// print other useful metadata
		System.out.println("  last updated: " + doc.getUpdated().toString());
		System.out.println("  viewed by user? " + doc.isViewed());
		System.out.println("  writersCanInvite? " + doc.isWritersCanInvite().toString());
		System.out.println("  hidden? " + doc.isHidden());
		System.out.println("  starrred? " + doc.isStarred());
		System.out.println();
	}

	public static void main(String args[]) throws Exception {
		GoogleComm comm = new GoogleComm(null);
		comm.login("frigus.glacialis@gmail.com", "testpassword");
		System.out.println("Done");
		comm.showAllDocs();
		System.out.println("Success");
	}
}
