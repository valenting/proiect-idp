package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


import app.Log;
import app.Mediator;

/**
 * Class used to communicate with the server and other clients
 */ 
public class Communicator {
	SocketChannel chan = null;
	Selector selector;
	Mediator m;
	public Communicator(Mediator m)  {
		this.m = m;
	}

	public void read(final SelectionKey key) throws  Exception {
		DataContainer data		= (DataContainer)key.attachment();		
		SocketChannel socket	= (SocketChannel)key.channel();
		int bytesRead = 0;
		try {

			while (data.lengthByteBuffer.remaining()!=0) {
				bytesRead = socket.read(data.lengthByteBuffer);
				if (bytesRead < 0 ) {
					m.loginError("Connection failure");
					key.channel().close();
					return;
				}
			}
			if (data.lengthByteBuffer.getInt(0)<0)
				throw new IOException();

			data.dataByteBuffer = ByteBuffer.allocate(data.lengthByteBuffer.getInt(0));
			if (data.dataByteBuffer == null)
				throw new IOException();
			data.lengthByteBuffer.clear();

			while (data.dataByteBuffer.remaining()!=0) {
				bytesRead = socket.read(data.dataByteBuffer);
				if (bytesRead < 0 ) {
					m.loginError("Connection failure");
					key.channel().close();
					return;
				}
			}

			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data.dataByteBuffer.array()));
			Serializable ret = (Serializable) ois.readObject();
			// clean up
			data.dataByteBuffer = null;
			data.readLength = true;
			((S2CMessage)ret).execute(m);
			
		} catch (NotYetConnectedException e) {
			
		} catch (IOException e) {
			m.loginError("Connection failure");
			e.printStackTrace();
			key.channel().close();
		} catch (OutOfMemoryError e) {
			m.loginError("Connection failure: Out Of Mem");
			e.printStackTrace();
			key.channel().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean connect(String IP, int PORT)  {
		try {
			chan = SocketChannel.open();
			chan.configureBlocking(false);

			selector = Selector.open();
			chan.register(selector, SelectionKey.OP_READ, new DataContainer());
			

		} catch (Exception e) {
			Log.error("SocketChannel Failed");
			return false;
		}
		
		try {
			chan.connect(new InetSocketAddress(IP, PORT));
			while(!chan.finishConnect()); // wait for connection to complete
			
			Thread t = new Thread() {
				public void run() {
					while (true) {
						try {
							selector.select();
							for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
								SelectionKey key = it.next();
								it.remove();
								if (key.isReadable())
									read(key);
							}
						} catch (NotYetConnectedException e) {
							Log.debug("Not Yet Connected so do nothing");
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			};
			t.start();
			return true;
			
		} catch (Exception e) {
			Log.error("Connection failed: "+e);
			m.loginError("Connection failed");
			return false;
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
			int bytesOut = 0;
			while (bytesOut<bs.size()) {
				int out = chan.write(wrap);
				if (out<0) {
					Log.error("CLOSED");
					chan.close();
				}
				bytesOut += out;
			}
		} catch (Exception e) {
			Log.error("Send Failed: "+e);
			m.loginError("Connection failure");
			try {
				chan.close();
			} catch (IOException e1) {
				Log.error("WTF");
			}
			
		}
	}


}

class DataContainer {
	ByteBuffer buf = null;
	ByteBuffer lengthByteBuffer = ByteBuffer.wrap(new byte[4]);
	boolean readLength = true;
	ByteBuffer dataByteBuffer = null;
}

