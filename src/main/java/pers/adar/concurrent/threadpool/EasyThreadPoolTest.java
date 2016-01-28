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
package pers.adar.concurrent.threadpool;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

public class EasyThreadPoolTest {

	@Test
	public void test() throws InterruptedException {
		EasyThreadPool threadPool = new EasyThreadPool(2);
		
		final CountDownLatch latch = new CountDownLatch(6);
		Runnable task1 = new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " task1");
				latch.countDown();
			}
		};

		Runnable task2 = new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " task2");
				latch.countDown();
			}
		};

		Runnable task3 = new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " task3");
				latch.countDown();
			}
		};
		
		threadPool.execute(task1);
		threadPool.execute(task1);
		threadPool.execute(task2);
		threadPool.execute(task2);
		threadPool.execute(task3);
		threadPool.execute(task3);
		
		latch.await();
		threadPool.shutdown();
	}
}
