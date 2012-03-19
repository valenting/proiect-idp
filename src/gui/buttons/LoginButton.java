package gui.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

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
		med = md;
		user = username;
		pass = password;
	}

	public void execute() {
		// TODO: 
		System.out.println(user.getText()+" | " + String.copyValueOf(pass.getPassword()));
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