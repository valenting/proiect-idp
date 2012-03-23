package gui.buttons;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

import app.Mediator;
import app.State;

public class FreeButton extends ToolbarButton {
	private static final long serialVersionUID = -4564644149844515769L;

	public FreeButton(ActionListener act, Mediator md) {
		super(act,md);
		this.setIcon(new ImageIcon("src/gui/images/curve.gif"));
		setToolTipText("Draw free");
		this.type = State.FREE;
	}
}