package gui.drawings;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

public class Arrow extends Drawing {
	
	public Arrow(int x, int y) {
		super(x, y);
	}

	public Arrow(Point start) {
		super(start);
	}
	
	private static final int ARR_SIZE = 10;
	
	public void draw(Graphics g1) {
		
		Graphics2D g = (Graphics2D) g1.create();

        double dx = end.x - start.x, dy = end.y - start.y;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(start.x, start.y);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.setTransform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, (int) len, 0);
        g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                      new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);

		
	}
}
