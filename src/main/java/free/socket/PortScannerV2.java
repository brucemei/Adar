package free.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 端口扫描 v2.0
 * 
 * 采用多线程并发扫描
 */
public class PortScannerV2 {
	
	/**
	 * 连接超时 ms
	 */
	private static int TIME_OUT_CONNECT = 200;
	
	private static ExecutorService executor = Executors.newCachedThreadPool();
	
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
			executor.execute(new Scanner(host, port));
		}
		
		executor.shutdown();
	}
	
	static class Scanner implements Runnable {
		
		private String host;
		
		private int port;
		
		public Scanner(String host, int port) {
			super();
			this.host = host;
			this.port = port;
		}

		public void run() {
			Socket socket = new Socket();
			try {
				System.out.println("Scan port...   Port: " + port);
				
				socket.connect(new InetSocketAddress(host, port), TIME_OUT_CONNECT);
				System.err.println("Port is listen: " + port);
			} catch (UnknownHostException e) {
				System.out.println("Host wrong.");
			} catch (IOException e) {
				// do nothing
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
