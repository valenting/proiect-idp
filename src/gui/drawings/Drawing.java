package gui.drawings;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Drawing {

	protected int x, y, w, h;

	protected Rectangle rect;

	public void draw(Graphics g) {
	}

	public void move(int xpt, int ypt) {
		x = xpt;
		y = ypt;
	}

	public boolean contains(int x, int y) {
		return rect.contains(x, y);
	}

	protected void saveAsRect() {
		rect = new Rectangle(x - w / 2, y - h / 2, w, h);
	}


}
