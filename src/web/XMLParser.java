package web;

import gui.drawings.Arrow;
import gui.drawings.Circle;
import gui.drawings.Drawing;
import gui.drawings.FreeDrawing;
import gui.drawings.Line;
import gui.drawings.Square;
import gui.drawings.Star;

import java.awt.Color;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLParser {
	Document doc;
	public XMLParser(String str1) {
		String str = str1.substring(str1.lastIndexOf("<?xml"));
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(str));
			doc = db.parse(is);
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Vector<Drawing> getDrawings() {
		Vector<Drawing> dr = new Vector<Drawing>();
		NodeList nodeList = doc.getElementsByTagName("drawing");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node fstNode = nodeList.item(i);
			if (fstNode.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			Drawing newDr = null;
			String name = fstNode.getAttributes().getNamedItem("name").getNodeValue();
			Color color = new Color(Integer.parseInt(fstNode.getAttributes().getNamedItem("color").getNodeValue()));
			NodeList points = ((Element)fstNode).getElementsByTagName("point");
			for (int j = 0; j < points.getLength(); j++) {
				Node n = points.item(j);
				int x1 = Integer.parseInt(n.getAttributes().getNamedItem("x").getNodeValue());
				int y1 = Integer.parseInt(n.getAttributes().getNamedItem("y").getNodeValue());
				if (newDr == null) {
					if (name.equals("free")) {
						newDr = new FreeDrawing(x1, y1);
					}
					if (name.equals("square")) {
						newDr = new Square(x1, y1);
					}
					if (name.equals("circle")) {
						newDr = new Circle(x1, y1);
					}
					if (name.equals("star")) {
						newDr = new Star(x1, y1);
					}
					if (name.equals("arrow")) {
						newDr = new Arrow(x1, y1);
					}
					if (name.equals("line")) {
						newDr = new Line(x1, y1);
					}
				}
				else
					newDr.move(x1, y1);
			}
			if (newDr != null) {
				newDr.setColor(color);
				dr.add(newDr);
			}
		}
		return dr;
	}

	public static void main(String args[]) throws Exception {
		String xml = "<?xml version=\"1.0\"?><drawings><drawing name = \"circle\" color=\"-16711681\"><point x=\"1\" y=\"2\" /><point x=\"3\" y=\"4\" /></drawing></drawings>";
		XMLParser p = new XMLParser(xml);
		Vector<Drawing>d = p.getDrawings();
		System.out.println(d.get(0));
	}
}
