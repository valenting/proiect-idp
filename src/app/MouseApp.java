package app;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseApp extends MouseAdapter {
	Mediator med;

	public MouseApp(Mediator md) {
		super();
		med = md;
	}

	public void mousePressed(MouseEvent e) {
		System.out.println("press");
		med.mousePressed(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) {
		System.out.println("release");
		med.mouseReleased(e.getX(), e.getY());
	}

	public void mouseDragged(MouseEvent e) {
		System.out.println("drag");
		med.mouseDragged(e.getX(), e.getY());
	}
}
