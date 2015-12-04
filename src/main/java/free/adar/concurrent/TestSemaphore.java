package free.adar.concurrent;

import java.util.concurrent.Semaphore;

public class TestSemaphore {
	
	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(10);
		
		semaphore.tryAcquire();
		semaphore.tryAcquire();
		semaphore.tryAcquire();
		semaphore.tryAcquire();
		
		System.out.println(semaphore.availablePermits());
	}
}
