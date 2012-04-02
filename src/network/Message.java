package network;

import java.io.Serializable;

public abstract class Message implements Serializable {
	private static final long serialVersionUID = -7784455849848939000L;
	abstract public void execute();
}


class LogInMessage extends Message {
	private static final long serialVersionUID = 4346500831789339892L;

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}

class LogOutMessage extends Message {
	private static final long serialVersionUID = 7513141582984081456L;

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
}