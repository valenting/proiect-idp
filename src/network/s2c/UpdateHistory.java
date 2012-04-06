package network.s2c;

import javax.swing.text.DefaultStyledDocument;

import app.Mediator;
import network.S2CMessage;

public class UpdateHistory extends S2CMessage {
	private static final long serialVersionUID = -4389660395272100035L;
	
	
	DefaultStyledDocument document;
	public UpdateHistory(DefaultStyledDocument document) {
		this.document = document;
	}
 
	@Override
	public void execute(Mediator m) {
		// TODO Auto-generated method stub
		
	}

}
