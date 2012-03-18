package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;

import worker.ExportTask;
import buttons.CircleButton;
import buttons.ClearButton;
import buttons.ExportButton;
import buttons.RectangleButton;
import buttons.UndoButton;
import drawings.JCanvas;

import app.Mediator;
import app.MouseApp;
import app.MouseMoveApp;


/**
 * Graphical User Interface Implementation
 */
public class Gui extends JFrame{
	private static final long serialVersionUID = 1L;
	Mediator med = null;
	
	JList users;
	JTree groups;
	JTabbedPane tabs;
	JPopupMenu groupMenu;
	
	
	
	public Gui(Mediator m) {
		super("White board");
		this.setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		
		JPanel jp = new JPanel();
		jp.setSize(400, 100);
		getContentPane().add(jp);
		med = new Mediator();

		GridBagLayout gbl = new GridBagLayout();
		gbl.layoutContainer(this);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.fill   = GridBagConstraints.BOTH;
		constraints.weightx = 500;
		constraints.weighty = 400;
			
		gbl.setConstraints(jp, constraints);
		
		jp.setLayout(gbl);
		
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		jp.add(users, constraints);
		
		setSize(new Dimension(500, 400));
		setVisible(true);

		
	}
}
