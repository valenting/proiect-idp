package gui;

import java.awt.Graphics;

public class Circle extends Drawing {

	public Circle(int xpt, int ypt) {
		x = xpt;
		y = ypt;
		w = 40;
		h = 30;
		saveAsRect();
	}

	public void draw(Graphics g) {
		g.drawArc(x, y, w, h, 0, 360);
	}
}