package gui;

import java.awt.Graphics;

public class Line extends Drawing {

	public Line(int xpt, int ypt) {
		x = xpt;
		y = ypt;
		w = xpt + 40;
		h = ypt + 30;
		saveAsRect();
	}

	public void draw(Graphics g) {
		g.drawLine(x, y, w, h);
	}
}
