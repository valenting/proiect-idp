package gui.buttons;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import app.Command;
import app.Mediator;

public class CreateGroup extends JButton implements Command {

	private static final long serialVersionUID = 1L;
	Mediator med;
	public CreateGroup(ActionListener act, Mediator m) {
		super("CreateGroup");
		addActionListener(act); 
		med = m;
		setToolTipText("Create Group button");
	}
	public void execute() {
		// TODO: med.createGroup();
	}

}
