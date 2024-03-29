package gui.buttons;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;

import app.Command;
import app.Mediator;

public class ToolbarButton extends JToggleButton implements Command {
	private static final long serialVersionUID = -1934805925856773261L;
	protected int type;
	Mediator med;

	public ToolbarButton(ActionListener act, Mediator md) {
		super();
		setSize(new Dimension(35, 35));
		setBorderPainted(true);
		addActionListener(act);
		med = md;
	}

	public Dimension getPreferredSize() {
		return new Dimension(40, 40);
	}

	public void execute() {
		med.menuSelection(this);
	}

	public int getType() {
		return type;
	}

}