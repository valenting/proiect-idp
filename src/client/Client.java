package client;

import app.Mediator;
import gui.Gui;

public class Client {

	public static void main(String []args) {
		Mediator m = new Mediator();
		new Gui(m);
	}
}


/*

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class Client {

	public static void main(String args[]) throws IOException {
		SocketChannel chan = SocketChannel.open();
		chan.configureBlocking(false);
		chan.connect(new InetSocketAddress("127.0.0.1", 7777));
		chan.finishConnect();
		while (!chan.isConnected()) {
			//System.out.println("Connecting");
		}
		System.out.println("Connected");
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		for(int i=0;i<4;i++) bs.write(0);
		ObjectOutputStream os = new ObjectOutputStream(bs);
		os.writeObject(new Integer(5));
		os.close();
		ByteBuffer wrap = ByteBuffer.wrap(bs.toByteArray());
		wrap.putInt(0, bs.size()-4);
		chan.write(wrap);
		System.out.println("Sent");
		while (true) ;
	}
	
}


*/