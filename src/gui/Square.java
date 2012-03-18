package gui;

import java.awt.Graphics;

public class Square extends Drawing {

	public Square(int xpt, int ypt) {
		x = xpt;
		y = ypt;
		w = 30;
		h = 30;
		saveAsRect();
	}

	public void draw(Graphics g) {
		g.drawRect(x, y, w, h);
	}
}