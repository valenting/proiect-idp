package web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.acl.AclEntry;
import com.google.gdata.data.acl.AclFeed;
import com.google.gdata.data.acl.AclRole;
import com.google.gdata.data.acl.AclScope;
import com.google.gdata.data.docs.DocumentEntry;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.extensions.LastModifiedBy;
import com.google.gdata.data.media.MediaByteArraySource;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import app.Mediator;

public class GoogleComm {
	private DocsService service;
	private String host = "docs.google.com";

	private final static int ADD_ROLE = 1;
	private final static int REMOVE_ROLE = 2;
	private final static int CHANGE_ROLE = 3;

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
		// Check if the document already exists
		URL feedUri = new URL("https://docs.google.com/feeds/documents/private/full/");
		DocumentListFeed feed = service.getFeed(feedUri, DocumentListFeed.class);
		for (DocumentListEntry entry : feed.getEntries()) {
			if (entry.getTitle().getPlainText().equals(title)) {
				return entry;
			}
		}
		// Else create a new Entry
		DocumentListEntry newEntry = new DocumentEntry();
		newEntry.setTitle(new PlainTextConstruct(title));
		URL url = new URL("https://docs.google.com/feeds/documents/private/full/");
		return service.insert(url, newEntry);
	}

	public void trashDocument(String title) throws Exception {
		if (title == null)
			throw new Exception("Null resourceId");
		// Obtain resourceId based on the docs title
		String resourceId = getResourceId(title);
		if (resourceId.length() <= 0)
			return;
		String feedUrl = "https://docs.google.com/feeds/documents/private/full/"
			+ resourceId + "?delete=true";
		service.delete(new URL(feedUrl), getDocsListEntry(resourceId).getEtag());
	}

	//TODO -use query to obtain documententry based on title
	private String getResourceId(String title) throws Exception {
		String resourceId = "";
		URL feedUri = new URL("https://docs.google.com/feeds/documents/private/full/");
		DocumentListFeed feed = service.getFeed(feedUri, DocumentListFeed.class);
		for (DocumentListEntry entry : feed.getEntries()) {
			if (entry.getTitle().getPlainText().equals(title)) {
				resourceId = entry.getResourceId();
				break;
			}
		}
		return resourceId;
	}

	private DocumentListEntry getDocsListEntry(String resourceId) throws Exception {
		if (resourceId == null)
			throw new Exception("Null resourceId");
		URL url = new URL("https://docs.google.com/feeds/documents/private/full/" + resourceId);

		return service.getEntry(url, DocumentListEntry.class);
	}

	public void downloadDocument(String title, String filePath, String format) throws Exception {
		// Obtain resourceId based on title
		if (title == null || filePath == null || format == null)
			throw new Exception("Null arguments");
		String resourceId = getResourceId(title);
		// If the resource doesn't exist
		if (resourceId.length() <= 0) {
			System.out.println("Nu exista\n");
			return;
		}
		String docType = resourceId.substring(0, resourceId.lastIndexOf(':'));
		String docId = resourceId.substring(resourceId.lastIndexOf(':') + 1);
		URL exportUrl = new URL("https://docs.google.com/feeds/download/" + docType +
				"s/Export?docID=" + docId + "&exportFormat=" + format);
		MediaContent mc = new MediaContent();
		mc.setUri(exportUrl.toString());
		MediaSource ms = service.getMedia(mc);

		InputStream inStream = null;
		FileOutputStream outStream = null;

		try {
			inStream = ms.getInputStream();
			outStream = new FileOutputStream(filePath);

			int c;
			while ((c = inStream.read()) != -1) {
				outStream.write(c);
			}
		} finally {
			if (inStream != null) {
				inStream.close();
			}
			if (outStream != null) {
				outStream.flush();
				outStream.close();
			}
		}
	}

	//TODO - now it just replaces the content
	// Later add new data
	public void addData(String title, String data) throws Exception {
		DocumentListEntry ent = getDocsListEntry(getResourceId(title));
		service.getRequestFactory().setHeader("If-Match", ent.getEtag());

		ent.setMediaSource(new MediaByteArraySource(data.getBytes(), "text/plain"));
		ent.updateMedia(false);
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
		System.out.println("PERMS:");
		try {
			showPerms(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("-----");
	}

	public AclEntry addAclRole(AclRole role, AclScope scope, DocumentListEntry documentEntry) throws Exception  {
		AclEntry entry = new AclEntry();
		entry.setRole(role);
		entry.setScope(scope);

		URL url = new URL("https://docs.google.com/feeds/acl/private/full/" + documentEntry.getResourceId());
		entry = service.insert(url, entry);
		return entry;
	}

	public AclEntry changeAclRole(AclRole role, AclScope scope, DocumentListEntry documentEntry)
	throws Exception {
		if (role == null || scope == null || documentEntry == null) {
			throw new Exception("Null passed in for required parameters");
		}
		URL url = new URL("https://docs.google.com/feeds/acl/private/full/" + documentEntry.getResourceId());

		return service.update(url, scope, role);
	}

	public void removeAclRole(AclScope scope, DocumentListEntry documentEntry) throws Exception {
		if (scope == null || documentEntry == null) {
			throw new Exception("null passed in for required parameters");
		}
		service.delete(new URL(documentEntry.getAclFeedLink().getHref()), scope);
	}

	public void showPerms(DocumentListEntry ent) throws Exception {
		AclFeed aclFeed = service.getFeed(new URL(ent.getAclFeedLink().getHref()), AclFeed.class);
		for (AclEntry entry : aclFeed.getEntries()) {
			System.out.println(
					entry.getScope().getValue() + " (" + entry.getScope().getType() + ") : " + entry.getRole().getValue());
		}
	}

	public void setPerms(DocumentListEntry ent, String user, AclRole role, int perm) throws Exception {
		AclScope scope = new AclScope(AclScope.Type.USER, user);
		switch(perm) {
		case ADD_ROLE:
			addAclRole(role, scope, ent);
			break;
		case REMOVE_ROLE:
			removeAclRole(scope, ent);
			break;
		case CHANGE_ROLE:
			changeAclRole(role, scope, ent);
			break;
		}

	}

	public static void main(String args[]) throws Exception {
		GoogleComm comm = new GoogleComm(null);
		comm.login("frigus.glacialis@gmail.com", "testpassword");
		System.out.println("Done");
		DocumentListEntry ent = comm.createNew("Test");
		//comm.setPerms(ent, "emma.mirica@gmail.com", AclRole.WRITER, ADD_ROLE);
		//comm.showPerms(ent);
		//comm.showAllDocs();
		comm.addData("Test", "hai sa vedem ce iese!");
		//comm.addData("Test", "hai sa vedem ce iese!");
		System.out.println("Success");
	}
}
