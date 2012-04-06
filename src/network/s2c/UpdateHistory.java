package network.s2c;

import javax.swing.SwingUtilities;
import javax.swing.text.DefaultStyledDocument;

import app.Mediator;
import network.S2CMessage;

public class UpdateHistory extends S2CMessage {
	private static final long serialVersionUID = -4389660395272100035L;
	
	
	DefaultStyledDocument document;
	String groupName;
	public UpdateHistory(String groupName, DefaultStyledDocument document) {
		this.document = document;
		this.groupName = groupName;
	}
 
	@Override
	public void execute(final Mediator m) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				m.setHistory(groupName, document);
			}
		});
		
	}

}
