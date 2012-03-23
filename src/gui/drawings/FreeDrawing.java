package gui.drawings;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

public class FreeDrawing extends Drawing {

	Vector<Point> points;
	
	public FreeDrawing(int x, int y) {
		super(x, y);
		points = new Vector<Point>();
		points.add(start);
	}

	public FreeDrawing(Point start) {
		super(start);
		points = new Vector<Point>();
		points.add(start);
	}
	
	public void move(int x, int y) {
		points.add(end);
		end = new Point(x,y);
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		for (int i=0;i<points.size()-1;i++)
			g.drawLine(points.get(i).x, points.get(i).y, points.get(i+1).x,points.get(i+1).y);
		g.drawLine(points.get(points.size()-1).x, points.get(points.size()-1).y, end.x, end.y);
	}
}
