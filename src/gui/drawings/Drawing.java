package gui.drawings;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Drawing implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Point start;
	protected Point end;
	public Color color;
	
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

	public String getName() {
		return "drawing";
	}
	
	public Node getXmlNode(Document doc) {
		String tagName = this.getName();
		Element el = doc.createElement("drawing");
		el.setAttribute("name", tagName);
		el.setAttribute("color", color.getRGB()+"");
		Element p1 = doc.createElement("point");
		Element p2 = doc.createElement("point");
		p1.setAttribute("x", start.x+"");
		p1.setAttribute("y", start.y+"");
		
		p2.setAttribute("x", end.x+"");
		p2.setAttribute("y", end.y+"");
		
		el.appendChild(p1);
		el.appendChild(p2);
		
		return el;
	}
	
}
