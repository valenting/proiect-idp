package gui.drawings;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class Drawing {

	protected Point start;
	protected Point end;
	
	public Drawing(Point start) {
		this.start = new Point(start);
		this.end = new Point(start.x+1,start.y+1);
	}
	
	public Drawing(int x, int y) {
		this.start = new Point(x,y);
		this.end = new Point(x+1,y+1);
	}
	
	public void draw(Graphics g) {
	}

	public void move(int x, int y) {
		end.x = x;
		end.y = y;
	}

}
