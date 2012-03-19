package gui.buttons;

import java.awt.event.ActionListener;
import javax.swing.JButton;

import app.Command;
import app.Mediator;

public class LogOutButton extends JButton implements Command {
	private static final long serialVersionUID = -1934805925856773261L;
	Mediator med;

	public LogOutButton(ActionListener act, Mediator md) {
		super("Log Out");
		addActionListener(act);
		med = md;
	}

	public void execute() {
		med.logOut();
	}

}