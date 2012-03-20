package gui;

import gui.buttons.LoginButton;

import javax.swing.JPanel;

import app.Mediator;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class Login extends JPanel {
	private static final long serialVersionUID = 1L;
	public JTextField textField; 
	private JPasswordField passwordField;

	/**
	 * Create the panel.
	 */
	public Login(Gui g, Mediator m) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.1, 0.0, 0.005, 0.0, 0.005, 0.0, 0.0, 0.0, 0.0, 0.0, 0.2, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Please insert username and password");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 4;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 5;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblUs = new JLabel("Username: ");
		GridBagConstraints gbc_lblUs = new GridBagConstraints();
		gbc_lblUs.gridwidth = 2;
		gbc_lblUs.insets = new Insets(0, 0, 5, 5);
		gbc_lblUs.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblUs.gridx = 4;
		gbc_lblUs.gridy = 3;
		add(lblUs, gbc_lblUs);
		
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.SOUTHWEST;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 6;
		gbc_textField.gridy = 3;
		add(textField, gbc_textField);
		textField.setColumns(10);
		textField.requestFocus();
		
		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 5;
		gbc_lblPassword.gridy = 5;
		add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.anchor = GridBagConstraints.NORTHWEST;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.gridx = 6;
		gbc_passwordField.gridy = 5;
		add(passwordField, gbc_passwordField);
		
		
		
		final JButton btnLogin = new LoginButton(g, m, textField, passwordField);
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.anchor = GridBagConstraints.WEST;
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 6;
		gbc_btnLogin.gridy = 6;
		add(btnLogin, gbc_btnLogin);
		
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
		
		JLabel lblStatusNotConnected = new JLabel("Status: Not Connected");
		GridBagConstraints gbc_lblStatusNotConnected = new GridBagConstraints();
		gbc_lblStatusNotConnected.insets = new Insets(0, 0, 0, 5);
		gbc_lblStatusNotConnected.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblStatusNotConnected.gridx = 6;
		gbc_lblStatusNotConnected.gridy = 10;
		add(lblStatusNotConnected, gbc_lblStatusNotConnected);

	}

}
