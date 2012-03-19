package gui.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import app.Command;
import app.Mediator;

public class ArrowButton extends JToggleButton implements Command {
	private static final long serialVersionUID = -1934805925856773261L;
	Mediator med;

	public ArrowButton(ActionListener act, Mediator md) {
		super(new ImageIcon("src/gui/images/square.gif"));
		setSize(new Dimension(35, 35));
		setBorderPainted(true);
		setToolTipText("Draw circle");
		addActionListener(act);
		med = md;
	}

	public Dimension getPreferredSize() {
		return new Dimension(35, 35);
	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	public void execute() {
		// TODO: 
	}

}