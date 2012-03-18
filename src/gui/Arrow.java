package gui;

import java.awt.Graphics;

public class Arrow extends Drawing{
	
	public Arrow(int xpt, int ypt) {
		x = xpt;
		y = ypt;
		w = 40;
		h = 30;
		saveAsRect();
	}

	public void draw(Graphics g) {
		//TODO - draw arrow
		g.drawRect(x, y, w, h);
	}
}
