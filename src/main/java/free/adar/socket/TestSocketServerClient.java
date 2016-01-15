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
package free.adar.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TestSocketServerClient
 */
@SuppressWarnings("all")
public class TestSocketServerClient {
	
	private static final String request = "POST /TestJavaEE/TestServlet1 HTTP/1.1\r\n" + 
			 							  "Host: localhost:10080\r\n" + 
			 							  "Connection: keep-alive\r\n" + 
			 							  "Cache-Control: no-cache\r\n" + 
			 							  "Content-Type: application/json\r\n" + 
			 							  "Content-Length: 3\r\n" + 
			 							  "\r\n" + 
			 							  "123";
	
	private static final String response = "HTTP/1.1 200 OK\r\n" +
			 							   "Connection: keep-alive\r\n" + 
			 							   "Content-Length: 2\r\n" + 
			 							   "\r\n" + 
			 							   "ok";
	
	public static void main(String[] args) throws IOException {
		testClient();
	}
	
	private static void testServer() throws IOException {
		ServerSocket serverSocket = new ServerSocket(10080);
		
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				System.out.println("Client: " + socket.getInetAddress().getHostAddress());
				
				InputStream inputStream = socket.getInputStream();
				
				byte[] buf = new byte[1024];
				int read = inputStream.read(buf);
				System.out.println(new String(buf, 0, read));
				
				socket.getOutputStream().write(response.getBytes());
			} catch (IOException e) {
				continue;
			}
		}
	}
	
	private static void testClient() throws IOException {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("localhost", 8080));

		OutputStream outputStream = socket.getOutputStream();
		InputStream inputStream = socket.getInputStream();
		
		PrintStream printStream = new PrintStream(outputStream, true);
		printStream.print(request);
		
		byte[] buf = new byte[2024];
		int read = inputStream.read(buf);
		
		System.out.println(new String(buf, 0, read));
		
		socket.close();
	}
}
