package gui.drawings;

import java.awt.Graphics;
import java.awt.Point;

public class Star extends Drawing {

	public Star(int x, int y) {
		super(x, y);
	}

	public Star(Point start) {
		super(start);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		int x1 = Math.min(start.x, end.x);
		int y1 = Math.min(start.y, end.y);
		int x2 = Math.max(start.x, end.x);
		int y2 = Math.max(start.y, end.y);
		drawStar(g, 5, x1, y1, x2 - x1, y2 - y1);
	}

	public double circleX(int sides, int angle) {
		double coeff = (double)angle/(double)sides;
		return Math.cos(2*coeff*Math.PI-Math.PI/2);
	}

	public double circleY(int sides, int angle) {
		double coeff = (double)angle/(double)sides;
		return Math.sin(2*coeff*Math.PI-Math.PI/2);
	}

	public void drawStar(Graphics g, int sides, int x, int y, int w, int h) {
		for(int i = 0; i < sides; i++) {
			int x1 = (int)(circleX(sides,i) * (double)(w)) + x;
			int y1 = (int)(circleY(sides,i) * (double)(h)) + y;
			int x2 = (int)(circleX(sides,(i+2)%sides) * (double)(w)) + x;
			int y2 = (int)(circleY(sides,(i+2)%sides) * (double)(h)) + y;
			g.drawLine(x1,y1,x2,y2);
		}
	}
}
