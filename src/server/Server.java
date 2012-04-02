package server;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	
	public static final int BUF_SIZE	= 10;			// buffer size
	public static final String IP		= "127.0.0.1";	// server IP
	public static final int PORT		= 7777;		// server port
	
	public static ExecutorService pool = Executors.newFixedThreadPool(5);	// thread pool - 5 threads
	
	public static void accept(SelectionKey key) throws IOException {
		
		System.out.print("ACCEPT: ");
		
		// TODO 2.6: register new socket with selector, use 'buf' as attachment
		
		ServerSocketChannel serverSocketChannel = null; // initialize from key
		SocketChannel socketChannel = null;				// initialize from accept
		
		Selector select = key.selector();

		ByteBuffer buf = ByteBuffer.allocateDirect(BUF_SIZE);
		serverSocketChannel = (ServerSocketChannel) key.channel();
		socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(select, SelectionKey.OP_READ, buf);
		// display remote client address
		System.out.println("Connection from: " + socketChannel.socket().getRemoteSocketAddress());
	}
	
	
	static ByteBuffer lengthByteBuffer = ByteBuffer.wrap(new byte[4]);
	private static boolean readLength = true;
	private static ByteBuffer dataByteBuffer = null;

	
	public static void read(final SelectionKey key) throws  Exception {
		
		ByteBuffer buf				= (ByteBuffer)key.attachment();		
		SocketChannel socket	= (SocketChannel)key.channel();
		System.out.println("READ");
		if (readLength) {
	        socket.read(lengthByteBuffer);
	        if (lengthByteBuffer.remaining() == 0) {
	            readLength = false;
	            dataByteBuffer = ByteBuffer.allocate(lengthByteBuffer.getInt(0));
	            lengthByteBuffer.clear();
	        }
	    } else {
	        socket.read(dataByteBuffer);
	        if (dataByteBuffer.remaining() == 0) {
	            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dataByteBuffer.array()));
	            final Serializable ret = (Serializable) ois.readObject();
	            // clean up
	            dataByteBuffer = null;
	            readLength = true;
	            System.out.println("RESULT "+(Integer)ret);
	        }
	    }
	
	}
	
	public static void write(SelectionKey key) throws IOException {
		
		System.out.println("WRITE: ");
		
		ByteBuffer buf				= (ByteBuffer)key.attachment();		
		SocketChannel socketChannel	= (SocketChannel)key.channel();
		
		int write = 0;
		
		while(true) {
			write = socketChannel.write(buf);
			if (write < 0) {
				socketChannel.close();
			}
			if (write <= 0) {
				break;
			}
		}
		if (buf.hasRemaining() == false) {
			buf.clear();
			key.interestOps(SelectionKey.OP_READ);
		}
	}
	
	public static void main(String[] args) throws Exception {
		
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
					else if (key.isWritable())
						write(key);
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

}
