package app;

import gui.Arrow;
import gui.Circle;
import gui.Line;
import gui.Square;

public class State {
	public void mouseClick(int x, int y) {
	}

	public void mouseMove(int x, int y) {
	}
}

class SquareState extends State {
	private Mediator med;
	private Square s;

	public SquareState(Mediator m) {
		med = m;
		s = null;
	}

	public void mouseClick(int x, int y) {
		med.addDrawing(s = new Square(x,y));
	}

	public void mouseMove(int x, int y) {
		s.move(x, y);
	}
}

class CircleState extends State {
	private Mediator med;
	private Circle c;
	
	public CircleState(Mediator m) {
		med = m;
		c = null;
	}

	public void mouseClick(int x, int y) {
		med.addDrawing(c = new Circle(x,y));
	}

	public void mouseMove(int x, int y) {
		c.move(x, y);
	}
}

class LineState extends State {
	private Mediator med;
	private Line l;

	public LineState(Mediator m) {
		med = m;
		l = null;
	}

	public void mouseClick(int x, int y) {
		med.addDrawing(l = new Line(x,y));
	}

	public void mouseMove(int x, int y) {
		l.move(x, y);
	}
}

class ArrowState extends State {
	private Mediator med;
	private Arrow a;

	public ArrowState(Mediator m) {
		med = m;
		a = null;
	}

	public void mouseClick(int x, int y) {
		med.addDrawing(a = new Arrow(x,y));
	}

	public void mouseMove(int x, int y) {
		a.move(x, y);
	}
}

//TODO - add other shapes