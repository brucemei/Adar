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

package pers.adar.concurrent;

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
