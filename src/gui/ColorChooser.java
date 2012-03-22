package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import app.Mediator;

import java.util.Vector;

public class ColorChooser extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ColorChooser(Vector<Color> colors, final Mediator m, final String user, final String group) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		final JComboBox box = new JComboBox(colors);
		box.setBackground(Color.gray);
		box.setForeground(Color.red);
		contentPane.add(box);
		setContentPane(contentPane);
		setVisible(true);
		setLocationRelativeTo(null);
		setSize(400, 100);
		
		box.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				Color c = (Color) box.getSelectedItem(); 
				m.joinGroupCommand(user,group,c); 	
				dispose();
			}
		});
	}

}
