package gui.buttons;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import app.Mediator;

public class CircleButton extends ToolbarButton {
	private static final long serialVersionUID = -4564644149844515769L;
	public CircleButton(ActionListener act, Mediator md) {
		super(act,md);
		this.setIcon(new ImageIcon("src/gui/images/circle.gif"));
		setToolTipText("Draw circle");
	}
}