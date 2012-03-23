package gui.drawings;

import java.awt.Graphics;
import java.awt.Point;

public class Line extends Drawing {

	public Line(int x, int y) {
		super(x, y);
	}

	public Line(Point start) {
		super(start);
	}
	
	public void draw(Graphics g) {
		g.drawLine(start.x, start.y, end.x, end.y);
	}
}
