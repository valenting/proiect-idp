package gui.buttons;


import gui.GroupTab;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import app.Command;
import app.Mediator;

public class SendButton extends JButton implements Command {

	private static final long serialVersionUID = 1L;
	Mediator med;
	GroupTab tab;
	public SendButton(ActionListener act, Mediator m, GroupTab tab) {
		super("Send");
		addActionListener(act); 
		med = m;
		this.tab = tab;
		setToolTipText("Send button");
	}

	public void execute() {
		// TODO Auto-generated method stub
		med.sendText(tab.getText(), tab.getFontSize(), tab.getFontColor(), tab); 
	}

}
