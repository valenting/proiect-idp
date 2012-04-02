package server;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.Mediator;

import network.Message;

public class Server {

	public final String IP		= "127.0.0.1";	// server IP
	public final int PORT		= 7777;		// server port


	ServerMediator m;

	public Server() {
		m = new ServerMediator(this);
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
	}



	public void read(final SelectionKey key) throws  Exception {

		DataContainer data		= (DataContainer)key.attachment();		
		SocketChannel socket	= (SocketChannel)key.channel();
		System.out.println("READ");
		int bytesRead = 0;
		if (data.readLength) {
			bytesRead = socket.read(data.lengthByteBuffer);
			if (data.lengthByteBuffer.remaining() == 0) {
				data.readLength = false;
				data.dataByteBuffer = ByteBuffer.allocate(data.lengthByteBuffer.getInt(0));
				data.lengthByteBuffer.clear();
			}
		} else {
			bytesRead = socket.read(data.dataByteBuffer);
			if (data.dataByteBuffer.remaining() == 0) {
				System.out.println(data.dataByteBuffer);
				ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data.dataByteBuffer.array()));
				Serializable ret = (Serializable) ois.readObject();
				// clean up
				data.dataByteBuffer = null;
				data.readLength = true;
				System.out.println("RESULT "+ret);
				((Message)ret).execute(m, key);
				// key.interestOps(SelectionKey.OP_WRITE);
			}
		}

		if (bytesRead<0)
			socket.close();
	}

	public void write(SelectionKey key, Message m) throws IOException {

		try {
			System.out.println("WRITE: ");
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			for(int i=0;i<4;i++) bs.write(0);
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(m);
			os.close();
			ByteBuffer wrap = ByteBuffer.wrap(bs.toByteArray());
			wrap.putInt(0, bs.size()-4);
			((SocketChannel)key.channel()).write(wrap);
		} catch (Exception e) {
			System.out.println("End");
		}
	}

	public void exec() throws Exception {

		Selector selector						= null;
		ServerSocketChannel serverSocketChannel	= null;

		try {
			// TODO 2.2: init selector
			selector = Selector.open();

			// TODO 2.3: init server socket and register it with the selector
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(IP, PORT));
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
