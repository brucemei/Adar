package free.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 错误HTTP响应码400
 */
@SuppressWarnings("all")
public class TestSocketServerClient {
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
				
				socket.close();
			} catch (IOException e) {
				continue;
			}
		}
	}
	
	private static void testClient() throws IOException {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("localhost", 10080));
		
		OutputStream outputStream = socket.getOutputStream();
		InputStream inputStream = socket.getInputStream();
		
		PrintStream printStream = new PrintStream(outputStream, true);
		printStream.println("test");
		
		byte[] buf = new byte[1024];
		int read = inputStream.read(buf);
		
		System.out.println(new String(buf, 0, read));
		System.out.println("over");
		
		socket.close();
	}
}
