package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import app.Mediator;

import java.util.Vector;

public class ColorChooser extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	
	// TODO : choose color mus be the first option in the list
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
		box.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color c = (Color) box.getSelectedItem(); 
				m.joinGroupCommand(user,group,c);
				dispose();
			}
		});
	}

}
