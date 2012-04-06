package gui.drawings;

import java.awt.Graphics;
import java.awt.Point;

public class Circle extends Drawing {
	private static final long serialVersionUID = 1L;

	public Circle(int x, int y) {
		super(x, y);
	}

	public Circle(Point start) {
		super(start);
	}

	public void draw(Graphics g) {

		int x1 = Math.min(start.x, end.x);
		int y1 = Math.min(start.y, end.y);
		int x2 = Math.max(start.x, end.x);
		int y2 = Math.max(start.y, end.y);

		g.setColor(color);
		g.drawArc(x1, y1, x2-x1, y2-y1, 0 , 360);
	}


}