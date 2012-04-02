package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Vector;

/**
 * Class used to communicate with the server and other clients
 */ 
public class Communicator {
	SocketChannel chan = null;
	Selector selector;
	public Communicator()  {
		try {
			chan = SocketChannel.open();
			chan.configureBlocking(false);
		
			selector = Selector.open();
			chan.register(selector, SelectionKey.OP_READ);
			Thread t = new Thread() {
				public void run() {
					while (true) {
						try {
							selector.select();
							for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
								// get current event and REMOVE it from the list!!!
								System.out.println("Event");
								SelectionKey key = it.next();
								it.remove();
								if (key.isReadable())
									read(key);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
			};
			t.start();
			
		} catch (Exception e) {
			System.err.println("SocketChannel Failed");
		}
		
		
	}

public static void read(final SelectionKey key) throws  Exception {
		
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
	        }
	    }
		
		if (bytesRead<0)
			socket.close();
	}
	
	public void connect(String IP, int PORT)  {
		try {
			chan.connect(new InetSocketAddress(IP, PORT));
			chan.finishConnect();
		} catch (Exception e) {
			System.err.println("Connection failed");
		}
	}

	public void send(Serializable obj)  {
		try {
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			for(int i=0;i<4;i++) bs.write(0);
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(obj);
			os.close();
			ByteBuffer wrap = ByteBuffer.wrap(bs.toByteArray());
			wrap.putInt(0, bs.size()-4);
			chan.write(wrap); 
		} catch (Exception e) {
			System.err.println("Send Failed");
		}
	}


}

class DataContainer {
	ByteBuffer buf = null;
	ByteBuffer lengthByteBuffer = ByteBuffer.wrap(new byte[4]);
	boolean readLength = true;
	ByteBuffer dataByteBuffer = null;
}

