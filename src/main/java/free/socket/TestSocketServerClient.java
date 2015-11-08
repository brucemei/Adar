package free.socket;

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
	public static void main(String[] args) throws IOException {
		testServer();
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
				
				String out = "HTTP/1.1 200 OK\r\n" +
							 "Connection: keep-alive\r\n" + 
							 "Content-Length: 0\r\n" + 
							 "\r\n" + 
							 "";
				
				socket.getOutputStream().write(out.getBytes());
			} catch (IOException e) {
				continue;
			}
		}
	}
	
	private static void testClient() throws IOException {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("localhost", 8080));

		long currentTimeMillis = System.currentTimeMillis();
		
		OutputStream outputStream = socket.getOutputStream();
		InputStream inputStream = socket.getInputStream();
		
		String out = "POST /TestJavaEE/TestServlet1 HTTP/1.1\r\n" + 
					 "Host: localhost:10080\r\n" + 
					 "Connection: keep-alive\r\n" + 
					 "Content-Length: 6\r\n" + 
					 "Cache-Control: no-cache\r\n" + 
					 "Content-Type: application/json\r\n" + 
					 "Accept: */*\r\n" + 
					 "Accept-Encoding: gzip, deflate\r\n" + 
					 "Accept-Language: zh-CN,zh;q=0.8\r\n" + 
					 "\r\n" + 
					 "123";
		
		PrintStream printStream = new PrintStream(outputStream, true);
		printStream.println(out);
		
		byte[] buf = new byte[2024];
		int read = inputStream.read(buf);
		System.out.println(System.currentTimeMillis() - currentTimeMillis);
		
		System.out.println(new String(buf, 0, read));
		System.out.println("over");
		
		socket.close();
	}
}
