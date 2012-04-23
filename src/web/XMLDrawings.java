package web;

import gui.drawings.Circle;
import gui.drawings.Drawing;
import gui.drawings.FreeDrawing;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class XMLDrawings {
	Document doc = null;
	Node root;
	public XMLDrawings() throws Exception {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader("<?xml version='1.0'?><drawing></drawing>"));
		doc = db.parse(is);
		root = doc.getChildNodes().item(0);
	}
	
	public void addDrawing(Drawing d) {
		root.appendChild(d.getXmlNode(doc));
	}
	
	public String toString() {
		return XMLtoTXT.stringXML(doc);
	}
	
	public static void main(String args[]) throws Exception {
		XMLDrawings d = new XMLDrawings();
		d.addDrawing(new Circle(1, 2));
		System.out.println(d);
		d.addDrawing(new FreeDrawing(0, 0));
		System.out.println(d);
		
	}
}

class XMLtoTXT {
	static Transformer trans = null;
	static StreamResult result = null;
	static StringWriter sw = null;
	public static void init() {
		TransformerFactory transfac = TransformerFactory.newInstance();
        trans = null;
		try {
			trans = transfac.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
		
		sw = new StringWriter();
        result = new StreamResult(sw);
	}
	
	public static String stringXML(Document doc) {
		if (trans == null)
			init();
		DOMSource source = new DOMSource(doc);
        try {
			trans.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
        String xmlString = sw.toString();
        return xmlString;
	}
}
