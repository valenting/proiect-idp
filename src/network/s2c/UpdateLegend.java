package network.s2c;

import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

import app.Mediator;
import network.S2CMessage;

public class UpdateLegend extends S2CMessage {
	private static final long serialVersionUID = -4389660395272100035L;
	
	
	DefaultListModel groupLegend;
	String groupName;
	public UpdateLegend(String groupName, DefaultListModel groupLegend) {
		this.groupName = groupName;
		this.groupLegend = groupLegend;
	}

	@Override
	public void execute(final Mediator m) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				m.setUserLegend(groupName, groupLegend);
			}
		});
		
	}

}
