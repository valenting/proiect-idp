package gui;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import app.Mediator;

public class GoogleLogin extends JFrame {
	private static final long serialVersionUID = -4095002598650843097L;
	private JTextField passwordField;
	private JTextField textField_1;
	private JButton btnLogin;
	Mediator med;
	public GoogleLogin(Mediator m) {
		med = m;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.2, 0.0, 0.0, 0.3, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.4, 0.0, 0.1, 0.0, 0.1, 0.2, 0.4, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblUser = new JLabel("Email:");
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.anchor = GridBagConstraints.EAST;
		gbc_lblUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblUser.gridx = 3;
		gbc_lblUser.gridy = 3;
		getContentPane().add(lblUser, gbc_lblUser);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.gridx = 4;
		gbc_textField_1.gridy = 3;
		getContentPane().add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 3;
		gbc_lblPassword.gridy = 5;
		getContentPane().add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 4;
		gbc_textField.gridy = 5;
		getContentPane().add(passwordField, gbc_textField);
		passwordField.setColumns(10);
		
		btnLogin = new JButton("Login");
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 3;
		gbc_btnLogin.gridy = 7;
		getContentPane().add(btnLogin, gbc_btnLogin);
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean succ = med.gLogin(textField_1.getText(), passwordField.getText());
				if (succ)
					dispose();
				else
					passwordField.setText("");
			}
		});
		
		passwordField.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) { }
			public void keyPressed(KeyEvent arg0) { }

			/**
			 * Pressing enter clicks the button
			 */
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) 
					btnLogin.doClick(10);
			}
		});
		setVisible(true);
		setLocationRelativeTo(null);
		setSize(400, 400);
	}

}
