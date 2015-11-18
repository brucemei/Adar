package free.adar.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 端口扫描
 */
public class PortScanner {
	
	/**
	 * 连接超时 ms
	 */
	private static int TIME_OUT_CONNECT = 200;
	
	public static void main(String[] args) {
		scan("localhost", 8070, 10000);
	}
	
	/**
	 * Port scan
	 */
	private static void scan(String host, int start, int end) {
		if (start < 0 || start > end) {
			throw new IllegalArgumentException();
		}
		
		for (int port = start; port < end; port++) {
			Socket socket = null;
			try {
				System.out.println("Scan port...   Port: " + port);
				
				socket = new Socket();
				socket.connect(new InetSocketAddress(host, port), TIME_OUT_CONNECT);
				
				System.err.println("Port is listen: " + port);
			} catch (UnknownHostException e) {
				System.out.println("Host wrong: " + host);
				break;
			} catch (IOException e) {
				continue;
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {}
				}
			}
		}
	}
}
