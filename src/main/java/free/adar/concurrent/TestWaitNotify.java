
package free.adar.concurrent;

public class TestWaitNotify {

	private static TestWaitNotify lock = new TestWaitNotify();

	/**
	 * 线程的交替执行
	 */
	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					synchronized (lock) {
						System.out.println(Thread.currentThread().getName() + " 消费");

						try {
							lock.notify();
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, "消费者").start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					synchronized (lock) {
						System.out.println(Thread.currentThread().getName() + " 生产");

						try {
							lock.notify();
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			}
		}, "生产者").start();
	}
}
