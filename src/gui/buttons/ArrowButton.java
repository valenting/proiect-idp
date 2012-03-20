package gui.buttons;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import app.Command;
import app.Mediator;

public class ArrowButton extends ToolbarButton {
	private static final long serialVersionUID = -4564644149844515769L;
	public ArrowButton(ActionListener act, Mediator md) {
		super(act,md);
		this.setIcon(new ImageIcon("src/gui/images/arrow.gif"));
		setToolTipText("Draw arrow");
	}
}