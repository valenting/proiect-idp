package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import app.Mediator;


public class JCanvas extends JPanel {
	private static final long serialVersionUID = 5147981563232704067L;
	Mediator med;

	public JCanvas(Mediator md) {
		med = md;
		//med.registerCanvas(this);
		setBackground(Color.white);
	}
	

	public void paint(Graphics g) {
		super.paint(g);
		med.reDraw(g);
	}
}