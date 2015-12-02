package free.adar.concurrent;


public class TestInterrupt {

	public static void main(String[] args) {
		Thread testIn = new Thread("test interrupt") {

			@Override
			public void run() {
				try {
					Thread.sleep(100000);
				} catch (InterruptedException e) {
					System.out.println(Thread.currentThread().getName() + ": 我被中断啦");
					e.printStackTrace();
				}
			}
			
		};
		
		testIn.start();
		
		testIn.interrupt();
		
		System.out.println(testIn.isInterrupted());
	}
}
