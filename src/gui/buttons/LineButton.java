package gui.buttons;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import app.Mediator;

public class LineButton extends ToolbarButton {
	private static final long serialVersionUID = -4564644149844515769L;
	public LineButton(ActionListener act, Mediator md) {
		super(act,md);
		this.setIcon(new ImageIcon("src/gui/images/line.gif"));
		setToolTipText("Draw line");
	}
}