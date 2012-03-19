package gui.buttons;


import java.awt.event.ActionListener;

import javax.swing.JButton;

import app.Command;
import app.Mediator;

public class SendButton extends JButton implements Command {

	private static final long serialVersionUID = 1L;
	Mediator med;
	
	public SendButton(ActionListener act, Mediator m) {
		super("Send");
		addActionListener(act);
		med = m;
		setToolTipText("Send button");
	}
	
	public void execute() {
		// TODO Auto-generated method stub

	}

}
