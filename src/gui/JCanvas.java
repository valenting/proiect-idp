package gui;

import gui.drawings.Drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

import app.Mediator;


public class JCanvas extends JPanel {
	private static final long serialVersionUID = 5147981563232704067L;
	Mediator med;
	public JCanvas(Mediator md) {
		med = md;
		setBackground(Color.white);
	}

	public void paint(Graphics g) {
		super.paint(g);
		med.reDraw(g);
	}
}