package free.adar.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@SuppressWarnings("unused")
public class TestSocket {
	
	public static void main(String[] args) {
		testSocketBindWrongHost();
	}

	/**
	 * 测试绑定错误的IP地址
	 */
	@SuppressWarnings("resource")
	private static void testSocketBindWrongHost() {
		try {
			Socket socket = new Socket();
			socket.bind(new InetSocketAddress("250.1.23.1", 10086));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试Socket超时
	 */
	private static void testSocketTimeout() {
		Socket socket = null;
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress("localhost", 80), 1000);
//			socket.connect(new InetSocketAddress("localhost", 8080), 1000);
			
			System.out.println("Connect success.");
		} catch (IOException e) {
			System.out.println("Connect timeout.");
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {}
			}
		}
	}
}
