package server;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;


import network.C2SMessage;
import network.Message;

public class Server {
	public final int PORT		= 7777;		// server port

	ServerMediator m;
	Vector<SocketChannel> sockets;
	public Server() {
		m = new ServerMediator(this);
		sockets = new Vector<SocketChannel>();
	}

	public void accept(SelectionKey key) throws IOException {

		System.out.print("ACCEPT: ");

		ServerSocketChannel serverSocketChannel = null; // initialize from key
		SocketChannel socketChannel = null;				// initialize from accept

		Selector select = key.selector();

		DataContainer cont = new DataContainer();
		serverSocketChannel = (ServerSocketChannel) key.channel();
		socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(select, SelectionKey.OP_READ, cont);
		// display remote client address
		System.out.println("Connection from: " + socketChannel.socket().getRemoteSocketAddress());
		sockets.add(socketChannel);
	}



	public void read(final SelectionKey key)  {
		DataContainer data		= (DataContainer)key.attachment();		
		SocketChannel socket	= (SocketChannel)key.channel();
		System.out.println("READ");
		int bytesRead = 0;
		try {

			while (data.lengthByteBuffer.remaining()!=0) {
				bytesRead = socket.read(data.lengthByteBuffer);
				if (bytesRead < 0 ) {
					cleanup(key);
					return;
				}
			}

			data.dataByteBuffer = ByteBuffer.allocate(data.lengthByteBuffer.getInt(0));
			data.lengthByteBuffer.clear();

			while (data.dataByteBuffer.remaining()!=0) {
				bytesRead = socket.read(data.dataByteBuffer);
				if (bytesRead < 0 ) {
					cleanup(key);
					return;
				}
			}

			System.out.println(data.dataByteBuffer);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data.dataByteBuffer.array()));
			Serializable ret = (Serializable) ois.readObject();
			// clean up
			data.dataByteBuffer = null;
			data.readLength = true;
			System.out.println("RESULT "+ret);
			((C2SMessage)ret).execute(m, key);
		} catch (Exception e) {
			System.err.println("Disconnect: "+e);
			cleanup(key);
		}
	}
	
	public void cleanup(SelectionKey key) {
		System.err.println("Cleanup");
		SocketChannel chan = (SocketChannel) key.channel();
		sockets.remove(chan);
		m.disconnectUser(chan);
		try {
			chan.close();
		} catch (IOException e) {
			System.err.println("Socket Close error: "+e);
		}
	}

	public void write(SelectionKey key, Message m){
		write((SocketChannel)key.channel(),m);
	}

	public void write(SocketChannel	chan, Message m){

		try {
			System.out.println("WRITE: ");
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			for(int i=0;i<4;i++) bs.write(0);
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(m);
			os.close();
			ByteBuffer wrap = ByteBuffer.wrap(bs.toByteArray());
			wrap.putInt(0, bs.size()-4);
			int bytesOut = 0;
			while (bytesOut<bs.size()) {
				int out =  chan.write(wrap);
				if (out<0) { 
					cleanup(chan.keyFor(selector));
					return;
				}
				bytesOut += out;
			}
		} catch (Exception e) {
			System.err.println("Disconnect: "+e);
			cleanup(chan.keyFor(selector));
		}
	}


	public void broadcast(Message m) {
		for (SocketChannel sock : sockets)
			write(sock,m);

	}

	Selector selector;
	public void exec() throws Exception {

		//Selector selector						= null;
		ServerSocketChannel serverSocketChannel	= null;

		try {
			selector = Selector.open();

			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			// main loop
			while (true) {
				// wait for something to happen
				selector.select();

				// iterate over the events
				for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
					// get current event and REMOVE it from the list!!!
					SelectionKey key = it.next();
					it.remove();
					if (key.isAcceptable())
						accept(key);
					else if (key.isReadable())
						read(key);

				}
			}
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			// cleanup

			if (selector != null)
				try {
					selector.close();
				} catch (IOException e) {}

			if (serverSocketChannel != null)
				try {
					serverSocketChannel.close();
				} catch (IOException e) {}
		}

	}

	public static void main(String args[]) throws Exception {
		Server s = new Server();
		s.exec();
	}

}

class DataContainer {
	ByteBuffer buf = null;
	ByteBuffer lengthByteBuffer = ByteBuffer.wrap(new byte[4]);
	boolean readLength = true;
	ByteBuffer dataByteBuffer = null;
}
