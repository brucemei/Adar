
package free.adar.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {

	private static int count = 0;

	private static Lock lock = new ReentrantLock();
	private static Condition condition = lock.newCondition();

	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					lock.lock();
					System.out.println(Thread.currentThread().getName() + " " + count++);

					try {
						condition.signal();
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					lock.unlock();
				}
			}
		});

		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					lock.lock();
					System.out.println(Thread.currentThread().getName() + " " + count++);

					try {
						condition.signal();
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					lock.unlock();
				}
			}
		});

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		System.out.println(count);
	}
}
