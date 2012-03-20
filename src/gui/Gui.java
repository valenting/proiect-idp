package gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import app.Mediator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import app.Command;

/**
 * Graphical User Interface Implementation
 */
public class Gui extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	Mediator med = null;
	
	private GeneralGui contentPane;
	private Login loginPane;
	public Gui(Mediator m) {
		super("Whiteboard");
		med = m;
		med.setGui(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new GeneralGui(this, m);
		loginPane = new Login(this, m);
		setContentPane(loginPane);
		
		setSize(1100, 700);
		setVisible(true);
	}

	public void loginSuccessful(String username) {
		setContentPane(contentPane);
		contentPane.lblUsername.setText(username);
		this.validate();
		
	}

	public void actionPerformed(ActionEvent e) {
		((Command)e.getSource()).execute();
	}

	public void logOut() {
		setContentPane(loginPane);
		validate();
		loginPane.textField.requestFocus(); // TODO Create function in Login.java?
	}
	
	public void groupDialog() {
		GroupDialog d = new GroupDialog(med);
		d.setSize(200,200);
		d.setLocationRelativeTo(null);
		d.setVisible(true);
	}
}
