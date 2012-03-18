package app;

public class StateManager {
	private Mediator med = null;
	private State currentState;
	
	private CircleState cs = null;
	private SquareState ss = null;
	private ArrowState as = null;
	private LineState ls = null;
	
	public StateManager(Mediator m) {
		this.med = m;
		// TODO
		/*cs = new CircleState(m);
		ss = new SquareState(m);
		as = new ArrowState(m);
		ls = new LineState(m);*/
		currentState = ss;
	}
	
	public void setSquareState() {
		//set current state as square state
		currentState = ss;
	}

	public void setCircleState() {
		//set current state as circle state
		currentState = cs;
	}
	
	public void setLineState() {
		//set current state as line state
		currentState = ls;
	}
	
	public void setArrowState() {
		//set current state as arrow state
		currentState = as;
	}

	public void mouseClick(int x, int y) {
		//perform mouseClick on the currentState of the manager
		currentState.mouseClick(x, y);
	}

	public void mouseMove(int x, int y) {
		//perform mouseUp on the currentState of the manager
		currentState.mouseMove(x, y);
	}
	
	public void SetStateLoggedIn() {
		
	}
	
	public void SetStateLoggedOut() {
		
	}
}
