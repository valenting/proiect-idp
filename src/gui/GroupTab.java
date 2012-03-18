package gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JToolBar;
import java.awt.GridBagConstraints;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.border.MatteBorder;
import java.awt.Color;

public class GroupTab extends JPanel {
	private static final long serialVersionUID = -163956933872151109L;
	private JTextField textField;
	public GroupTab() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.1, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.5, Double.MIN_VALUE, 0.0};
		setLayout(gridBagLayout);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.insets = new Insets(0, 0, 5, 5);
		gbc_toolBar.gridx = 0;
		gbc_toolBar.gridy = 0;
		add(toolBar, gbc_toolBar);
		
		JToggleButton tglbtnA = new JToggleButton("A");
		toolBar.add(tglbtnA);
		
		JToggleButton tglbtnB = new JToggleButton("B");
		toolBar.add(tglbtnB);
		
		JToggleButton tglbtnC = new JToggleButton("C");
		toolBar.add(tglbtnC);
		
		JToggleButton tglbtnD = new JToggleButton("D");
		toolBar.add(tglbtnD);
		
		JButton btnSaveWork = new JButton("Save work");
		GridBagConstraints gbc_btnSaveWork = new GridBagConstraints();
		gbc_btnSaveWork.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveWork.gridx = 1;
		gbc_btnSaveWork.gridy = 0;
		add(btnSaveWork, gbc_btnSaveWork);
		
		JProgressBar progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 1;
		add(progressBar, gbc_progressBar);
		
		JLabel lblJpanel = new JLabel("Jpanel");
		lblJpanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_lblJpanel = new GridBagConstraints();
		gbc_lblJpanel.fill = GridBagConstraints.BOTH;
		gbc_lblJpanel.gridheight = 2;
		gbc_lblJpanel.insets = new Insets(0, 0, 5, 5);
		gbc_lblJpanel.gridx = 0;
		gbc_lblJpanel.gridy = 1;
		add(lblJpanel, gbc_lblJpanel);
		
		JList list = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 2;
		add(list, gbc_list);
		
		JTextPane textPane = new JTextPane();
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 5, 0);
		gbc_textPane.gridwidth = 2;
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 3;
		add(textPane, gbc_textPane);
		
		
		
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
		
		JButton btnSend = new JButton("Send");
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 5;
		add(btnSend, gbc_btnSend);
		
	}
	
}
