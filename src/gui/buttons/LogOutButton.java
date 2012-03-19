package gui.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

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