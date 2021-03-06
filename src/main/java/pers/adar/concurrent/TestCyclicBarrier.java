/**
 * Copyright (c) 2015, adar.w (adar-w@outlook.com) 
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

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class TestCyclicBarrier {

	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(3, () -> {
			System.out.println(Thread.currentThread().getName() + " Barrier Action");
		});
		
		for (int i = 0; i < 3; i++) {
			new Thread(() -> {
				
				System.out.println(Thread.currentThread().getName() + " Wait...");
				try {
					Thread.sleep(new Random().nextInt(3000));
				} catch (Exception e) {}
				
				try {
					barrier.await();
				} catch (Exception e) {}
				
				System.out.println(Thread.currentThread().getName() + " After wait");
			}).start();
		}

		for (int i = 0; i < 3; i++) {
			new Thread(() -> {
				
				System.out.println(Thread.currentThread().getName() + " Wait...");
				try {
					Thread.sleep(new Random().nextInt(3000));
				} catch (Exception e) {}
				
				try {
					barrier.await();
				} catch (Exception e) {}
				
				System.out.println(Thread.currentThread().getName() + " After wait");
			}).start();
		}
		
		System.out.println("I'm main.");
	}
}
