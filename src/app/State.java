package app;

import gui.drawings.Arrow;
import gui.drawings.Circle;
import gui.drawings.Drawing;
import gui.drawings.FreeDrawing;
import gui.drawings.Line;
import gui.drawings.Square;
import gui.drawings.Star;

public class State {
	public final static int CIRCLE = 1;
	public final static int SQUARE = 2;
	public final static int LINE = 3;
	public final static int ARROW = 4; 
	public final static int FREE = 5;
	public final static int STAR = 6;
	
	protected Mediator med;
	protected Drawing d;
	
	public State(Mediator m) {
		med = m;
		d = null;
	}
	
	public void mousePressed(int x, int y) {
	}

	public void mouseReleased(int x, int y) {
	}
	
	public void mouseDragged(int x, int y) {
		d.move(x, y);
	}
}

class SquareState extends State {

	public SquareState(Mediator m) {
		super(m);
	}

	public void mousePressed(int x, int y) {
		med.addDrawing(d = new Square(x,y));
	}

}

class CircleState extends State {
	public CircleState(Mediator m) {
		super(m);
	}

	public void mousePressed(int x, int y) {
		med.addDrawing(d = new Circle(x,y));
	}
}

class LineState extends State {
	public LineState(Mediator m) {
		super(m);
	}

	public void mousePressed(int x, int y) {
		med.addDrawing(d = new Line(x,y));
	}
}

class ArrowState extends State {
	public ArrowState(Mediator m) {
		super(m);
	}

	public void mousePressed(int x, int y) {
		med.addDrawing(d = new Arrow(x,y));
	}
}

class FreeState extends State {
	public FreeState(Mediator m) {
		super(m);
	}

	public void mousePressed(int x, int y) {
		med.addDrawing(d = new FreeDrawing(x,y));
	}
}

class StarState extends State {
	public StarState(Mediator m) {
		super(m);
	}

	public void mousePressed(int x, int y) {
		med.addDrawing(d = new Star(x,y));
	}
}