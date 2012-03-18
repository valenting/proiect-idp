package gui;

import java.awt.Dimension;
import javax.swing.JFrame;

import app.Mediator;

public class Gui extends JFrame{
	private static final long serialVersionUID = 1L;
	Mediator med = null;
	public Gui(Mediator m) {
		super("Chat");
		this.med = m;
		setLocationRelativeTo(null);
		setSize(new Dimension(1000, 600));
		setVisible(true);
	}
}
