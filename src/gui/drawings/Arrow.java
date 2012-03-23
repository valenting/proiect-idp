package gui.drawings;

import java.awt.Graphics;
import java.awt.Point;


public class Arrow extends Drawing {
	
	public Arrow(int x, int y) {
		super(x, y);
	}

	public Arrow(Point start) {
		super(start);
	}
	
	public void draw(Graphics g) {

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

		g.setColor(color);
		g.drawLine(start.x, start.y, (int) baseX, (int) baseY);
		g.fillPolygon(xPoints, yPoints, 3);
		
	}
}
