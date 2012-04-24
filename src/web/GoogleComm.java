package web;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.acl.AclEntry;
import com.google.gdata.data.acl.AclFeed;
import com.google.gdata.data.acl.AclRole;
import com.google.gdata.data.acl.AclScope;
import com.google.gdata.data.docs.DocumentEntry;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.docs.RevisionEntry;
import com.google.gdata.data.docs.RevisionFeed;
import com.google.gdata.data.media.MediaByteArraySource;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import app.Log;
import app.Mediator;
import app.Pair;

public class GoogleComm {
	private DocsService service;
	private final static int ADD_ROLE = 1;
	private final static int REMOVE_ROLE = 2;
	private final static int CHANGE_ROLE = 3;

	public GoogleComm(Mediator m) {
		service = new DocsService("Whiteboard");
		service.setProtocolVersion(DocsService.Versions.V3);
	}
	String user;
	String pass;
	/* Verifica credentialele de Google si face logarea daca 
	 * sunt corecte, altfel intoarce false
	 */
	public boolean login(String user, String pass)  {
		try {
			service = new DocsService("Whiteboard");
			service.setProtocolVersion(DocsService.Versions.V3);
			service.setUserCredentials(user,pass);
			this.user = user;
			this.pass = pass;
			return true;
		} catch (AuthenticationException e1) {
			Log.error("AuthenticationError " + e1.toString());
			return false;
		}
	}

	/* Creeaza un nou document, doar daca nu mai exista 
	 * un alt document cu acel nume -> grupurile sunt unice
	 */
	public DocumentListEntry createNew(String title) {
		login(user,pass);
		// Check if the document already exists
		String rid = getResourceId(title);
		if (!rid.equals("")) {
			Log.debug("Resource " + title + " already exists");
			return null;
		}

		DocumentListEntry newEntry = new DocumentEntry();
		newEntry.setTitle(new PlainTextConstruct(title));
		URL url;
		try {
			url = new URL("https://docs.google.com/feeds/default/private/full/");
		} catch (MalformedURLException e) {
			Log.debug("MalformedURLException + " + e.toString());
			return null;
		}
		try {
			return service.insert(url, newEntry);
		} catch (IOException e) {
			Log.debug("IOException + " + e.toString());
			return null;
		} catch (ServiceException e) {
			Log.debug("ServiceException + " + e.toString());
			return null;
		}
	}

	/* Sterge un fisier pe baza numelui */
	public void trashDocument(String title) {
		String resourceId = getResourceId(title);
		if (resourceId.equals("")) {
			Log.debug("Resource " + title + " doesn't exists");
			return;
		}
		DocumentListEntry ent = getDocument(title);
		if (ent == null)
			return;
		String feedUrl = "https://docs.google.com/feeds/default/private/full/" + resourceId + "?delete=true";
		try {
			service.delete(new URL(feedUrl), ent.getEtag());
		} catch (Exception e) {
			Log.debug(e.toString());
		}
	}

	private String getResourceId(String title) {
		DocumentListEntry ent = getDocument(title);
		if (ent != null)
			return ent.getResourceId();
		return "";
	}

	/* Obtine documentEntry pe baza numelui. Exista un singur
	 * document cu numele title Sau null daca nu exista
	 */
	public DocumentListEntry getDocument(String title) {
		URL feedUri;
		login(user,pass);
		//service.getRequestFactory().setHeader("If-Match", "*");
		try {
			feedUri = new URL("https://docs.google.com/feeds/default/private/full/");
		} catch (MalformedURLException e) {
			Log.debug("MalformedURLException + " + e.toString());
			return null;
		}
		DocumentListFeed feed;
		try {
			feed = service.getFeed(feedUri, DocumentListFeed.class);
		} catch (IOException e) {
			Log.debug("IOException + " + e.toString());
			return null;
		} catch (ServiceException e) {
			Log.debug("ServiceException + " + e.toString());
			return null;
		}
		for (DocumentListEntry entry : feed.getEntries())
			if (entry.getTitle().getPlainText().equals(title)) 
				return entry;
		return null;
	}

	/* Obtine continutul unui DocumentListEntry sau nimic daca apar probleme */
	public String getContent(DocumentListEntry ent, String format) {
		String content = "";
		String resourceId = ent.getResourceId();
		String docType = resourceId.substring(0, resourceId.lastIndexOf(':'));
		String docId = resourceId.substring(resourceId.lastIndexOf(':') + 1);
		URL exportUrl;
		try {
			exportUrl = new URL("https://docs.google.com/feeds/download/" + docType +
					"s/Export?docID=" + docId + "&exportFormat=" + format);
		} catch (MalformedURLException e1) {
			Log.debug("MalformedURLException " + e1.toString());
			return content;
		}
		MediaContent mc = new MediaContent();
		mc.setUri(exportUrl.toString());
		MediaSource ms;
		try {
			ms = service.getMedia(mc);
			InputStream inStream = ms.getInputStream();
			int c;
			while ((c = inStream.read())!=-1) {
				char ch = (char) c;
				if (ch != 'ï' && ch != '»' && ch != '¿')
					content += ch;
			}
		} catch (IOException e1) {
			Log.debug("IOException " + e1.toString());
			return content;
		} catch (ServiceException e1) {
			Log.debug("ServiceException " + e1.toString());

			return content;
		}

		return content;
	}

	/* Obtine continutul unui fisier pe baza numelui */
	public String getContent(String title) {
		DocumentListEntry entr = getDocument(title);
		if (entr == null) {
			Log.debug("DocumentEntry " + title + " doesn't exists");
			return "";
		}
		return getContent(entr, "txt");
	}

	/* Inlocuieste continutul documentului title cu data */
	public void addData(String title, String data) {
		DocumentListEntry ent = getDocument(title);
		if (ent == null) {
			Log.debug("Null document " + title);
			return;
		}
		addData(ent, data);
	}

	public void addData(DocumentListEntry ent, String data) {
		service.getRequestFactory().setHeader("If-Match", "*"); // 
		ent.setMediaSource(new MediaByteArraySource(data.getBytes(), "text/plain"));

		try {
			ent.updateMedia(true);
		} catch (IOException e) {
			Log.debug("IOException " + e.toString());
		} catch (ServiceException e) {
			Log.debug("ServiceException " + e.toString());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
			}
			/*addData(ent, data);*/
		}
	}

	public AclEntry addAclRole(AclRole role, AclScope scope, DocumentListEntry documentEntry) {
		AclEntry entry = new AclEntry();
		entry.setRole(role);
		entry.setScope(scope);

		URL url;
		try {
			url = new URL("https://docs.google.com/feeds/default/private/full/" + 
					documentEntry.getResourceId() + "/acl");
		} catch (MalformedURLException e) {
			Log.debug("MalformedURLException " + e.toString());
			return null;
		}
		try {
			entry = service.insert(url, entry);
		} catch (IOException e) {
			Log.debug("IOException " + e.toString());
			return null;
		} catch (ServiceException e) {
			Log.debug("ServiceException " + e.toString());
			return null;
		} catch (Exception e) {
			Log.debug(e.toString());
		}
		return entry;
	}

	public AclEntry changeAclRole(AclRole role, AclScope scope, DocumentListEntry documentEntry) {
		if (role == null || scope == null || documentEntry == null) {
			Log.debug("Null arguments");
			return null;
		}
		URL url;
		try {
			url = new URL("https://docs.google.com/feeds/default/private/full/" + documentEntry.getResourceId() + "/acl");
		} catch (MalformedURLException e) {
			Log.debug("MalformedURLException " + e.toString());
			return null;
		}

		try {
			return service.update(url, scope, role);
		} catch (IOException e) {
			Log.debug("IOException " + e.toString());
			return null;
		} catch (ServiceException e) {
			Log.debug("ServiceException " + e.toString());
			return null;
		}
	}

	public void removeAclRole(AclScope scope, DocumentListEntry documentEntry) {
		if (scope == null || documentEntry == null) {
			Log.debug("Null passed in for required parameters");
			return;
		}
		try {
			service.delete(new URL(documentEntry.getAclFeedLink().getHref()), scope);
		} catch (MalformedURLException e) {
			Log.debug("MalformedURLException " + e.toString());
			return;
		} catch (IOException e) {
			Log.debug("IOException " + e.toString());
			return;
		} catch (ServiceException e) {
			Log.debug("ServiceException " + e.toString());
			return;
		}
	}

	public void showPerms(DocumentListEntry ent) throws Exception {
		AclFeed aclFeed = service.getFeed(new URL(ent.getAclFeedLink().getHref()), AclFeed.class);
		for (AclEntry entry : aclFeed.getEntries()) {
			System.out.println(
					entry.getScope().getValue() + " (" + entry.getScope().getType() + ") : " + entry.getRole().getValue());
		}
	}

	public void setPerms(DocumentListEntry ent, String user, AclRole role, int perm) {
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

	public void addWriter(String email, String title) {
		DocumentListEntry entry = getDocument(title);
		if (entry == null)
			return;
		setPerms(entry, email, AclRole.WRITER, ADD_ROLE);
	}

	public String downloadDocument(String href) {
		String content = "";
		URL exportUrl = null;
		try {
			exportUrl = new URL(href);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		MediaContent mc = new MediaContent();
		mc.setUri(exportUrl.toString());
		MediaSource ms;
		try {
			ms = service.getMedia(mc);
			InputStream inStream = ms.getInputStream();
			int c;
			while ((c = inStream.read())!=-1) {
				char ch = (char) c;
				if (ch != 'ï' && ch != '»' && ch != '¿')
					content += ch;
			}
		} catch (IOException e1) {
			Log.debug("IOException " + e1.toString());
			return content;
		} catch (ServiceException e1) {
			Log.debug("ServiceException " + e1.toString());

			return content;
		}

		return content;
	}

	private RevisionFeed getRevisionFeed(String title) {
		DocumentListEntry ent = getDocument(title);
		if (ent == null)
			return null;
		String resId = ent.getResourceId();
		try {
			URL url = new URL("https://docs.google.com/feeds/default/private/full/"+resId+"/revisions");
			return service.getFeed(url, RevisionFeed.class);
		} catch (MalformedURLException e) {
			Log.debug("MalformedURLException " + e.toString());
		} catch (IOException e) {
			Log.debug("IOException " + e.toString());
		} catch (ServiceException e) {
			Log.debug("ServiceException " + e.toString());
		}
		return null;
	}

	public Vector<Pair<String, String>> getRevisions(String title) {
		Vector<Pair<String,String>> v = new Vector<Pair<String,String>>();
		DocumentListEntry ent = getDocument(title);
		RevisionFeed feed = getRevisionFeed(title);
		if (feed != null) {
			for (RevisionEntry e : feed.getEntries()) {
				String s = e.getTitle().getPlainText()+" created on ";
				s+= e.getUpdated().toUiString()+" by ";
				s+=e.getModifyingUser().getEmail()+"\n";
				String link = e.getEditLink().getHref();
				String content = downloadDocument("https://docs.google.com/feeds/download/documents/Export?docID="+ent.getDocId()+
						"&exportFormat=txt" + "&revision="+link.substring(link.lastIndexOf("/") + 1));
				v.add(new Pair<String, String>(s, content));
			}
		}
		return v;
	}

	public static void main(String args[]) throws Exception {
		GoogleComm comm = new GoogleComm(null);
		comm.login("frigus.glacialis@gmail.com", "testpassword");
		DocumentListEntry ent = comm.createNew("EDCRFVTGB");
		comm.addData("EDCRFVTGB", "testing...");
		comm.addData("EDCRFVTGB", "teafdsdfasdsfd.");
		comm.addWriter("valentin.gosu@gmail.com", "EDCRFVTGB");
		System.out.println(comm.getContent("EDCRFVTGB"));
		comm.addData("EDCRFVTGB", "funf");
		System.out.println(comm.getContent("EDCRFVTGB"));
		
		
	}
}
