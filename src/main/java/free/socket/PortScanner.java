package free.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 端口扫描
 */
public class PortScanner {
	
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
				
				socket = new Socket(host, port);
				System.err.println("Port is listen: " + port);
			} catch (UnknownHostException e) {
				System.out.println("Host wrong.");
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
