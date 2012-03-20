package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import app.Mediator;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	/**
	 * Create the dialog.
	 */
	Mediator med;
	JLabel lblStatus;
	public GroupDialog(Mediator m) {
		med = m;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblInsertAGroup = new JLabel("Insert a group name");
			GridBagConstraints gbc_lblInsertAGroup = new GridBagConstraints();
			gbc_lblInsertAGroup.insets = new Insets(0, 0, 5, 5);
			gbc_lblInsertAGroup.gridx = 2;
			gbc_lblInsertAGroup.gridy = 2;
			contentPanel.add(lblInsertAGroup, gbc_lblInsertAGroup);
		}
		{
			textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 5);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 2;
			gbc_textField.gridy = 4;
			contentPanel.add(textField, gbc_textField);
			textField.setColumns(10);
		}
		{
			lblStatus = new JLabel("");
			GridBagConstraints gbc_lblStatus = new GridBagConstraints();
			gbc_lblStatus.insets = new Insets(0, 0, 0, 5);
			gbc_lblStatus.gridx = 3;
			gbc_lblStatus.gridy = 7;
			contentPanel.add(lblStatus, gbc_lblStatus);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String t = textField.getText();
						if (med.groupExists(t)) {
							lblStatus = new JLabel("Group Exists");
						} else {
							System.out.println("group:"+t);
							med.addGroup(t); 
						}
						
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		}
	}

}
