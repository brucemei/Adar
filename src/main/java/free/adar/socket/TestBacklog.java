package free.adar.socket;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 检测当前环境Backlog上限
 */
public class TestBacklog {

	private static final int PORT = 10080;

	private static final int BACKLOG = 2048;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		new ServerSocket(PORT, BACKLOG);
		
		boolean fullOver = false;
		for (int i = 0; i < BACKLOG + 1; i++) {
			try {
				System.out.println("Check socket count: " + i);
				
				Socket socket = new Socket();
				socket.connect(new InetSocketAddress("localhost", PORT));
			} catch (ConnectException e) {
				if (BACKLOG > i) {
					System.out.println("Current environment upper limit: " + i);
				}
				
				break;
			}
			
			if (i == BACKLOG + 1) {
				fullOver = true;
			}
		}
		
		if (fullOver) {
			System.out.println("Current environment upper limit more than " + BACKLOG);
		}
	}
}
