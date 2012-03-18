package gui;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Container extends JPanel {
	public Container(Component c) {
		super(new GridLayout(1,0));
		add(new JScrollPane(c));
	}
	
}
