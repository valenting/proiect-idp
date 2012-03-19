package gui.buttons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import app.Command;
import app.Mediator;

public class RectangleButton extends JToggleButton implements Command {
	private static final long serialVersionUID = -1934805925856773261L;
	Mediator med;

	public RectangleButton(ActionListener act, Mediator md) {
		super(new ImageIcon("src/gui/images/square.gif"));
		setSize(new Dimension(35, 35));
		setBorderPainted(true);
		setToolTipText("Draw rectangle");
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