package gui.drawings;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public class Drawing implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Point start;
	protected Point end;
	protected Color color;

	public Drawing(Point start) {
		this.start = new Point(start);
		this.end = new Point(start.x+1,start.y+1);
		color = Color.black;
	}

	public Drawing(int x, int y) {
		this.start = new Point(x,y);
		this.end = new Point(x+1,y+1);
		color = Color.black;
	}

	public void draw(Graphics g) {
	}

	public void move(int x, int y) {
		end.x = x;
		end.y = y;
	}

	public void setColor(Color c) {
		color = c;
	}

}
