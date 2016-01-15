/**
 * Copyright (c) 2015, adar.w (adar.w@outlook.com) 
 * 
 * http://www.adar-w.me
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package free.adar.concurrent;

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
