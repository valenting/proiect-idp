package gui.drawings;

import java.awt.Graphics;
import java.awt.Point;

public class Line extends Drawing {
	private static final long serialVersionUID = 1L;

	public Line(int x, int y) {
		super(x, y);
	}

	public Line(Point start) {
		super(start);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.drawLine(start.x, start.y, end.x, end.y);
	}
}
