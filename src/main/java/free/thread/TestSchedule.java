
package free.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestSchedule {
	public static void main(String[] args) {
		ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

		System.out.println(System.currentTimeMillis());
		scheduledExecutor.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "  " + System.currentTimeMillis());
				System.out.println("ahahah");
			}
		}, 1L, 0L, TimeUnit.SECONDS);

		System.out.println("main thread");
	}
}
