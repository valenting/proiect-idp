package gui.buttons;


import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import app.Command;
import app.Mediator;

public class SendButton extends JButton implements Command {

	private static final long serialVersionUID = 1L;
	Mediator med;
	JTextField textField;
	public SendButton(ActionListener act, Mediator m, JTextField textField) {
		super("Send");
		addActionListener(act); 
		med = m;
		this.textField = textField;
		setToolTipText("Send button");
	}

	public void execute() {
		// TODO Auto-generated method stub
		System.out.println(textField.getText());
		textField.setText("");
	}

}
