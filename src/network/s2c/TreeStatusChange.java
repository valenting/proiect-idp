package network.s2c;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;

import app.Mediator;
import network.S2CMessage;

public class TreeStatusChange extends S2CMessage {
	private static final long serialVersionUID = -3558083632977837640L;
	DefaultTreeModel model;
	public TreeStatusChange(DefaultTreeModel model) {
		this.model = model;
	}
	
	@Override
	public void execute(final Mediator m) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				m.setTreeList(model);
			}
		});
		
	}

}
