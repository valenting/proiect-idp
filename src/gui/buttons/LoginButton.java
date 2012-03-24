package gui.buttons;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import app.Command;
import app.Mediator;

public class LoginButton extends JButton implements Command {
	private static final long serialVersionUID = -1934805925856773261L;
	Mediator med;
	JTextField user;
	JPasswordField pass;
	
	public LoginButton(ActionListener act, Mediator md, JTextField username, JPasswordField password) {
		super("Log In");
		addActionListener(act);
		setToolTipText("Log in button");
		med = md;
		user = username;
		pass = password;
	}

	public void execute() {
		String username = user.getText();
		String password = String.copyValueOf(pass.getPassword());
		user.setText("");
		pass.setText("");
		if (med.login(username, password)) {
			med.loginSuccessful(username);
		} else {
			med.loginError(); 
		}
	}

}