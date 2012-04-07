package app;

public class StateManager {
	private Mediator med = null;
	private State currentState;

	private CircleState cs = null;
	private SquareState ss = null;
	private ArrowState as = null;
	private LineState ls = null;
	private FreeState fs = null;
	private StarState sts = null;
	
	public StateManager(Mediator m) {
		med = m;
		cs = new CircleState(med);
		ss = new SquareState(med);
		as = new ArrowState(med);
		ls = new LineState(med);
		fs = new FreeState(med);
		sts = new StarState(med);
		// Default shape - circle
		currentState = cs;
	}

	public void mousePressed(int x, int y) {
		currentState.mousePressed(x, y);
	}

	public void mouseDragged(int x, int y) {
		currentState.mouseDragged(x, y);
	}

	public void mouseReleased(int x, int y) {
		currentState.mouseReleased(x, y);
	}

	public void setState(int type) {
		switch(type) {
		case State.CIRCLE:
			// set current state as circle state
			currentState = cs;
			break;
		case State.ARROW:
			// set current state as arrow state
			currentState = as;
			break;
		case State.LINE:
			// set current state as line state
			currentState = ls;
			break;
		case State.SQUARE:
			// set current state as square state
			currentState = ss;
			break;
		case State.FREE:
			// set current state as free drawing state
			currentState = fs;
			break;
		case State.STAR:
			// set current state as star state
			currentState = sts;
			break;
		default:
			Log.error("Invalid state " + type);
			currentState = cs;
			break;
		}
	}
}
