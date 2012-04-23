package gui.drawings;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class FreeDrawing extends Drawing {
	private static final long serialVersionUID = 1L;
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
	
	public String getName() {
		return "free";
	}
	
	public Node getXmlNode(Document doc) {
		String tagName = this.getName();
		Element el = doc.createElement(tagName);
		el.setAttribute("color", color.getRGB()+"");

		Element p1 = doc.createElement("point");
		p1.setAttribute("x", start.x+"");
		p1.setAttribute("y", start.y+"");
		el.appendChild(p1);
		
		for (int i=0;i<points.size();i++) {
			Element p = doc.createElement("point");
			p.setAttribute("x", points.get(i).x+"");
			p.setAttribute("y", points.get(i).y+"");
		}
		
		return el;
	}
}
