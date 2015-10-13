package free.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 错误HTTP响应码400
 */
public class TestSocket {
	public static void main(String[] args) throws IOException {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("172.16.34.199", 7080));
		
		OutputStream outputStream = socket.getOutputStream();
		InputStream inputStream = socket.getInputStream();
		
		PrintStream printStream = new PrintStream(outputStream, true);
		printStream.println("ahsadsadsadsds");
		
		byte[] buf = new byte[1024];
		inputStream.read(buf);
		
		System.out.println(new String(buf).trim());
		
		socket.close();
	}
}
