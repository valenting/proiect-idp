package gui.buttons;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

import app.Mediator;
import app.State;

public class StarButton extends ToolbarButton {
	private static final long serialVersionUID = -4564644149844515769L;

	public StarButton(ActionListener act, Mediator md) {
		super(act,md);
		this.setIcon(new ImageIcon("src/gui/images/star.gif"));
		setToolTipText("Draw star");
		this.type = State.STAR;
	}
}