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
