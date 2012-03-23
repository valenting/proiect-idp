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
	
	public void draw(Graphics g) {
		/*
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
		
        /*/
		
		float arrowWidth = 10.0f;
		float theta = 0.423f;
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		float[] vecLine = new float[2];
		float[] vecLeft = new float[2];
		float fLength;
		float th;
		float ta;
		float baseX, baseY;

		xPoints[0] = end.x;
		yPoints[0] = end.y;

		// build the line vector
		vecLine[0] = (float) xPoints[0] - start.x;
		vecLine[1] = (float) yPoints[0] - start.y;

		// build the arrow base vector - normal to the line
		vecLeft[0] = -vecLine[1];
		vecLeft[1] = vecLine[0];

		// setup length parameters
		fLength = (float) Math.sqrt(vecLine[0] * vecLine[0] + vecLine[1]* vecLine[1]);
		th = arrowWidth / (2.0f * fLength);
		ta = arrowWidth / (2.0f * ((float) Math.tan(theta) / 2.0f) * fLength);

		// find the base of the arrow
		baseX = ((float) xPoints[0] - ta * vecLine[0]);
		baseY = ((float) yPoints[0] - ta * vecLine[1]);

		// build the points on the sides of the arrow
		xPoints[1] = (int) (baseX + th * vecLeft[0]);
		yPoints[1] = (int) (baseY + th * vecLeft[1]);
		xPoints[2] = (int) (baseX - th * vecLeft[0]);
		yPoints[2] = (int) (baseY - th * vecLeft[1]);

		g.drawLine(start.x, start.y, (int) baseX, (int) baseY);
		g.fillPolygon(xPoints, yPoints, 3);
		
	}
}
