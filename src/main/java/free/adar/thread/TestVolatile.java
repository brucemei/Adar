
package free.adar.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class TestVolatile {

	private static AtomicInteger i = new AtomicInteger(0);

	public static void main(String[] args) throws InterruptedException {

		Runnable task = new Runnable() {

			@Override
			public void run() {
				for (int j = 0; j < 100000; j++) {
					i.incrementAndGet();
				}
			}
		};

		Thread t1 = new Thread(task);
		Thread t2 = new Thread(task);
		Thread t3 = new Thread(task);
		Thread t4 = new Thread(task);
		Thread t5 = new Thread(task);
		Thread t6 = new Thread(task);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();

		t1.join();
		t2.join();
		t3.join();
		t4.join();
		t5.join();
		t6.join();

		System.out.println(i);
	}
}
