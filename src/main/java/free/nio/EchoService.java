package free.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * Simple Echo Service
 */
public class EchoService {
	
	private static Selector SELECTOR;
	
	public static void main(String[] args) {
		start(10080);
	}
	
	public static void start(int port) {
		try {
			initSelector();
			
			initServer(port);
		} catch (IOException e) {
			e.printStackTrace();
			
			// Init fail.
			System.exit(1);
		}
		
		System.out.println("Server init successFul.");
		
		startServer();
	}

	private static void initSelector() throws IOException {
		SELECTOR = Selector.open();
	}
	
	private static void initServer(int port) throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		
		serverSocketChannel.register(SELECTOR, SelectionKey.OP_ACCEPT);
	}
	
	private static void startServer() {
		while (true) {
			try {
				SELECTOR.select();
			} catch (IOException e) {
				continue;
			}
			
			Set<SelectionKey> keys = SELECTOR.selectedKeys();
			for (SelectionKey key : keys) {
				if (key.isAcceptable()) {
					doAccept(key);
				} else if (key.isReadable()) {
					doRead(key);
				} else if (key.isWritable()) {
					doWrite(key);
				}
			}
			
			keys.clear();
		}
	}
	
	private static void doAccept(SelectionKey key) {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
		try {
			SocketChannel socketChannel = serverSocketChannel.accept();
			socketChannel.configureBlocking(false);
			
			System.out.println("Visitor: " + socketChannel.getRemoteAddress());
			
			socketChannel.register(SELECTOR, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void doRead(SelectionKey key) {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		try {
			ByteBuffer buffer = (ByteBuffer) key.attachment();
			int read = socketChannel.read(buffer);
			System.out.println("Read: " + read);
			if (read == -1) {
				key.cancel();
				socketChannel.close();
				
				System.out.println("Socket Close");
			} else {
				if (!buffer.hasRemaining()) {
					ByteBuffer newBuffer = ByteBuffer.allocate(buffer.limit() * 2);
					
					buffer.flip();
					newBuffer.put(buffer);
					
					key.attach(newBuffer);
				} else {
					key.interestOps(SelectionKey.OP_WRITE);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();

			key.cancel();
			try {
				socketChannel.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private static void doWrite(SelectionKey key) {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		try {
			ByteBuffer buffer = (ByteBuffer) key.attachment();
			buffer.flip();
			System.out.println("Msg: " + new String(buffer.array()));
			
			socketChannel.write(buffer);
			
			buffer.clear();
			
			key.interestOps(SelectionKey.OP_READ);
		} catch (IOException e) {
			e.printStackTrace();
			
			key.cancel();
			try {
				socketChannel.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
