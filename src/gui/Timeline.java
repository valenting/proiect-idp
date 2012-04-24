package gui;

import gui.drawings.Drawing;

import java.util.Vector;

import javax.swing.JFrame;

import web.GoogleComm;
import web.XMLParser;

import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import app.Pair;

public class Timeline extends JFrame {
	private static final long serialVersionUID = 386127950518737040L;
	JTextArea textArea;
	JSlider slider;
	JTimelinePanel panel;
	GoogleComm gcom;
	public Timeline(GoogleComm gcom, String title) {
		this.gcom = gcom;

		textArea = new JTextArea();
		getContentPane().add(textArea, BorderLayout.NORTH);

		final Vector<Pair<String,String>> v = gcom.getRevisions(title);
		if (v.size() > 0)
			textArea.setText(v.get(0).getK());

		slider = new JSlider(JSlider.HORIZONTAL, 0, v.size() - 1, 0);
		getContentPane().add(slider, BorderLayout.SOUTH);
		slider.setSnapToTicks(true);

		panel = new JTimelinePanel();
		panel.setBackground(Color.white);
		getContentPane().add(panel, BorderLayout.CENTER);

		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int id = slider.getValue();
				textArea.setText(v.get(id).getK());
				String content = v.get(id).getV();
				if (content.length() > 0) {
					XMLParser parser = new XMLParser(content);
					Vector<Drawing> dr = parser.getDrawings();
					panel.setTimelineImage(dr);
					panel.updateUI();
				}
			}
		});

		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setSize(700, 500);
	}
}

class JTimelinePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage image;

    public JTimelinePanel() {
       image = null;
    }
    
    public void setTimelineImage(Vector<Drawing> dr) {
    	image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());
		for (Drawing d : dr) {
			d.draw(g2);
		}
    }

    @Override
    public void paint(Graphics g) {
    	super.paint(g);
        ((Graphics2D)g).drawImage(image, 0, 0, this);
    }

}