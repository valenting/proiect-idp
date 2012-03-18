package app;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseApp extends MouseAdapter {
	Mediator med;

	public MouseApp(Mediator md) {
		super();
		med = md;
	}

	public void mousePressed(MouseEvent e) {
		med.mouseClick(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) {
		med.mouseMove(e.getX(), e.getY());
	}
}

class MouseMoveApp extends MouseMotionAdapter {
	Mediator med;

	public MouseMoveApp(Mediator md) {
		super();
		med = md;
	}
}
