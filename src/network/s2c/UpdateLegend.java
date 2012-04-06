package network.s2c;

import javax.swing.DefaultListModel;

import app.Mediator;
import network.S2CMessage;

public class UpdateLegend extends S2CMessage {
	private static final long serialVersionUID = -4389660395272100035L;
	
	
	DefaultListModel groupLegend;
	public UpdateLegend(DefaultListModel groupLegend) {
		this.groupLegend = groupLegend;
	}

	@Override
	public void execute(Mediator m) {
		// TODO Auto-generated method stub
		
	}

}
