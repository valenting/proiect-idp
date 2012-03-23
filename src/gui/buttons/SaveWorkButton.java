package gui.buttons;

import java.awt.event.ActionListener;
import javax.swing.JButton;

import app.Command;
import app.Mediator;

public class SaveWorkButton extends JButton implements Command {
	private static final long serialVersionUID = 1L;
	Mediator med;
	
	public SaveWorkButton(ActionListener act, Mediator m) {
		super("Save Work");
		med = m;
		addActionListener(act);
		setToolTipText("Export image");
	}

	public void execute() {
		med.saveWork();
	}

}
