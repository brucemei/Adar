package free.adar.socket;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 检测当前环境Backlog上限
 */
public class TestBacklog {

	private static final int PORT = 17777;

	private static final int BACKLOG = 20480;
	
	private static final AtomicInteger COUNT = new AtomicInteger();
	
	private static final AtomicBoolean ISOVER = new AtomicBoolean();

	private static final AtomicBoolean UPPERLIMIT = new AtomicBoolean();
	
	private static final int CONCURRENT = 17;
	
	private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(CONCURRENT);
	
	private static final CountDownLatch CDL = new CountDownLatch(1);
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException {
		new ServerSocket(PORT, BACKLOG);
		
		for (int i = 0; i < CONCURRENT; i++) {
			EXECUTOR.submit(new CheckSocket());
		}
		
		CDL.await();
		
		if (UPPERLIMIT.get()) {
			System.out.println("Current environment upper limit more than " + BACKLOG);
		}
		
		EXECUTOR.shutdown();
	}
	
	static class CheckSocket implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					synchronized (CheckSocket.class) {
						if (checkLimit() && !ISOVER.get()) {
							return;
						}

						System.out.println("Check socket count: " + COUNT.incrementAndGet());
						
						Socket socket = new Socket();
						socket.connect(new InetSocketAddress("localhost", PORT));
					}
				} catch (ConnectException e) {
					System.out.println("Current environment upper limit: " + COUNT.get());
					
					exit();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		private boolean checkLimit() {
			if (COUNT.get() >= BACKLOG) {
				UPPERLIMIT.set(true);

				exit();
				
				return true;
			}
			
			return false;
		}
		
		private void exit() {
			ISOVER.set(true);
			CDL.countDown();
		}
	}
}
