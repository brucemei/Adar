/**
 * Copyright (c) 2015, adar.w (adar.w@outlook.com) 
 * 
 * http://www.adar-w.me
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package free.adar.nio;

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
			
			System.out.println("[INFO] Visitor: " + socketChannel.getRemoteAddress());
			
			socketChannel.register(SELECTOR, SelectionKey.OP_READ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void doRead(SelectionKey key) {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		try {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int read = socketChannel.read(buffer);
			if (read == -1) {
				key.cancel();
				socketChannel.close();
				
				System.out.println("Socket Close");
			} else {
				while (!buffer.hasRemaining()) {
					ByteBuffer newBuffer = ByteBuffer.allocate(buffer.limit() * 2);
					
					buffer.flip();
					newBuffer.put(buffer);
					
					buffer = newBuffer;
					
					socketChannel.read(buffer);
				}
				System.out.println("[INFO] **************" + "Packet length: " + buffer.position() + "**************");

				key.attach(buffer);
				key.interestOps(SelectionKey.OP_WRITE);
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

			System.out.println(new String(buffer.array()));
			
			socketChannel.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			key.cancel();
			try {
				socketChannel.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
