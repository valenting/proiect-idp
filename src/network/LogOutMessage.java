package network;

import java.nio.channels.SelectionKey;

import app.Mediator;
import app.MediatorStub;

public class LogOutMessage extends Message {

	private static final long serialVersionUID = 4346500831789339892L;


	
	public String toString() {
		return "LogOutMessage";
	}



	@Override
	public void execute(MediatorStub m, SelectionKey key) {
		// TODO Auto-generated method stub
		
	}
	
}