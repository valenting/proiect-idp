package gui;

import gui.buttons.ArrowButton;
import gui.buttons.CircleButton;
import gui.buttons.LineButton;
import gui.buttons.RectangleButton;
import gui.buttons.SaveWorkButton;
import gui.buttons.SendButton;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JToolBar;
import java.awt.GridBagConstraints;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.ScrollPaneConstants;

import app.Mediator;

public class GroupTab extends JPanel {
	private static final long serialVersionUID = -163956933872151109L;
	private JTextField textField;
	JToolBar toolBar;
	JProgressBar progressBar;
	JPanel panel;
	JList userLegend;
	public GroupTab(Gui g, Mediator m) {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.1, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.5, Double.MIN_VALUE, 0.0};
		setLayout(gridBagLayout);

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.insets = new Insets(0, 0, 5, 5);
		gbc_toolBar.gridx = 0;
		gbc_toolBar.gridy = 0;
		add(toolBar, gbc_toolBar);


		JToggleButton tglbtnA = new CircleButton(g, m);
		tglbtnA.setSelected(true);
		toolBar.add(tglbtnA);

		JToggleButton tglbtnB = new RectangleButton(g, m);
		toolBar.add(tglbtnB);

		JToggleButton tglbtnC = new ArrowButton(g,m);
		toolBar.add(tglbtnC);

		JToggleButton tglbtnD = new LineButton(g, m);
		toolBar.add(tglbtnD);

		m.addGroupElement(tglbtnA, this);
		m.addGroupElement(tglbtnB, this);
		m.addGroupElement(tglbtnC, this);
		m.addGroupElement(tglbtnD, this);

		JButton btnSaveWork = new SaveWorkButton(g, m);
		GridBagConstraints gbc_btnSaveWork = new GridBagConstraints();
		gbc_btnSaveWork.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveWork.gridx = 1;
		gbc_btnSaveWork.gridy = 0;
		add(btnSaveWork, gbc_btnSaveWork);
		m.addGroupElement(btnSaveWork, this);

		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 1;
		add(progressBar, gbc_progressBar);
		m.addGroupElement(progressBar, this);

		// Canvas - Drawing area
		panel = new JCanvas(m);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		m.addGroupElement(panel, this);

		userLegend = new JList();
		userLegend.setModel(new AbstractListModel() {
			String[] values = new String[] {"User1", "User2", "User3","User1", "User2", "User3","User1", "User2", "User3"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});

		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 2;
		JScrollPane p = new JScrollPane(userLegend);
		p.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(p, gbc_list);


		// Discutii
		JTextPane txtpnHello = new JTextPane();
		txtpnHello.setText("hello");
		txtpnHello.setEditable(false);
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 5, 0);
		gbc_textPane.gridwidth = 2;
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 3;
		p = new JScrollPane(txtpnHello);
		p.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(p, gbc_textPane);



		JToolBar toolBar2 = new JToolBar();
		toolBar2.setFloatable(false);
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar2 = new GridBagConstraints();
		gbc_toolBar2.insets = new Insets(0, 0, 5, 5);
		gbc_toolBar2.gridx = 0;
		gbc_toolBar2.gridy = 4;
		add(toolBar2, gbc_toolBar2);

		JLabel lblFont = new JLabel("Font:");
		toolBar2.add(lblFont);

		JComboBox comboBox = new JComboBox(); 
		toolBar2.add(comboBox);

		JLabel lblColour = new JLabel("Colour:");
		toolBar2.add(lblColour);

		JComboBox comboBox_1 = new JComboBox();
		toolBar2.add(comboBox_1);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 5;
		add(textField, gbc_textField);
		textField.setColumns(10);
		
		

		final JButton btnSend = new SendButton(g, m, textField);
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 5;
		add(btnSend, gbc_btnSend);

		textField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) { }
			@Override
			public void keyPressed(KeyEvent e) { }
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					btnSend.doClick(10);
			}
		});
	}

	/**
	 * Used to pop the other selected buttons
	 * @param The clicked button
	 */
	public void popOthers(Object o) {
		for (int i=0;i<toolBar.getComponentCount();i++) {
			JToggleButton b = (JToggleButton) toolBar.getComponent(i);
			if (!b.equals(o))
				b.setSelected(false);
			else
				b.setSelected(true);
		}
	}

}
