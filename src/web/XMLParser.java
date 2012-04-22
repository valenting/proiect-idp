package web;

import gui.drawings.Arrow;
import gui.drawings.Circle;
import gui.drawings.Drawing;
import gui.drawings.FreeDrawing;
import gui.drawings.Line;
import gui.drawings.Square;
import gui.drawings.Star;

import java.io.StringReader;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.xml.internal.ws.util.xml.NodeListIterator;

public class XMLParser {
	Document doc;
	public XMLParser(String str) throws Exception {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(str));
		doc = db.parse(is);
	}

	Vector<Drawing> getDrawings() {
		NodeList nodes = doc.getChildNodes().item(0).getChildNodes();
		Vector<Drawing> d = new Vector<Drawing>();

		for (int i=0;i<nodes.getLength();i++) {
			Node e =  nodes.item(i);
			System.out.println(e.getNodeName());
			String name = e.getNodeName();
			Drawing dr = null;
			if (name.equals("free")) {
				for (int j=0;j<e.getChildNodes().getLength(); j++) {
					Node n = e.getChildNodes().item(j);
					int x1 = Integer.parseInt(n.getAttributes().getNamedItem("x").getNodeValue());
					int y1 = Integer.parseInt(n.getAttributes().getNamedItem("y").getNodeValue());
					if (dr == null) 
						dr = new FreeDrawing(x1, y1);
					else
						dr.move(x1, y1);
				}
				
			} else {
				
				Node p1 = e.getChildNodes().item(0);
				Node p2 = e.getChildNodes().item(1);
				
				int x1 = Integer.parseInt(p1.getAttributes().getNamedItem("x").getNodeValue());
				int y1 = Integer.parseInt(p1.getAttributes().getNamedItem("y").getNodeValue());
				
				int x2 = Integer.parseInt(p2.getAttributes().getNamedItem("x").getNodeValue());
				int y2 = Integer.parseInt(p2.getAttributes().getNamedItem("y").getNodeValue());
				
				if (name.equals("circle")) {
					dr =new Circle(x1, y1);
					dr.move(x2, y2);
				} else if (name.equals("square")) {
					dr = new Square(x1, y1);
					dr.move(x2, y2);
				} else if (name.equals("line")) {
					dr = new Line(x1, y1);
					dr.move(x2, y2);
				} else if (name.equals("arrow")) {
					dr = new Arrow(x1, y1);
					dr.move(x2, y2);
				} else if (name.equals("star")) {
					dr = new Star(x1, y1);
					dr.move(x2, y2);
				}
			}
			if (dr != null)
				d.add(dr);
		}

		return d;
	}

	public static void main(String args[]) throws Exception {
		String xml = "<?xml version='1.0'?><drawing><circle color=''><point x='1' y='2' /><point x='3' y='4' /></circle></drawing>";
		XMLParser p = new XMLParser(xml);
		Vector<Drawing>d =p.getDrawings();
		System.out.println(d.get(0));
	}
}
