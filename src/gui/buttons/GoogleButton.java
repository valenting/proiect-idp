package gui.buttons;

import java.awt.event.ActionListener;
import javax.swing.JButton;

import app.Command;
import app.Mediator;

public class GoogleButton extends JButton implements Command {

	private static final long serialVersionUID = 1L;
	Mediator med;

	public GoogleButton(ActionListener act, Mediator m) {
		super("Google Login");
		addActionListener(act); 
		med = m;
		setToolTipText("Login with Google");
	}

	public void execute() {
		med.openGLogin();
	}

}
