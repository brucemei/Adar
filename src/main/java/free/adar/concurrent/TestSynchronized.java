package free.adar.concurrent;

/**
 * 其他线程可以看到synchronized代码块中对变量的修改
 */
public class TestSynchronized {

	private int a = 0;
//	private volatile int a = 0;
	
	private void m1() throws InterruptedException {
		synchronized (TestSynchronized.class) {
			a = 1;
			Thread.sleep(1000);

			a = 2;
			Thread.sleep(1000);

			a = 3;
			Thread.sleep(1000);
		}
	}
	
	private void m2() {
		System.out.println(a);
	}
	
	public static void main(String[] args) {
		final TestSynchronized bean = new TestSynchronized();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (;;) {
					bean.m2();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "T-m2");

		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					bean.m1();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "T-m1");

		
		t1.start();
		t2.start();
	}
}
