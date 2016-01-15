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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试修改线程池线程数
 */
public class TestModifyThreadPool {
	public static void main(String[] args) {
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();

		final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 2, 5L, TimeUnit.SECONDS, workQueue,
				new RejectedExecutionHandler() {

					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.out.println("任务队列已满");
					}

				});

		new Thread("线程池修改线程") {

			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(30);
					System.out.println("当前核心线程数为: " + poolExecutor.getCorePoolSize() + "修改核心线程数为5");
					synchronized (this) {
						poolExecutor.setMaximumPoolSize(5);
						poolExecutor.setCorePoolSize(5);
					}

					TimeUnit.SECONDS.sleep(10);
					System.out.println("当前核心线程数为: " + poolExecutor.getCorePoolSize() + "修改核心线程数为2");
					synchronized (this) {
						poolExecutor.setCorePoolSize(2);
						poolExecutor.setMaximumPoolSize(2);
					}

					TimeUnit.SECONDS.sleep(10);
					System.out.println("当前核心线程数为: " + poolExecutor.getCorePoolSize() + "修改核心线程数为5");
					synchronized (this) {
						poolExecutor.setMaximumPoolSize(5);
						poolExecutor.setCorePoolSize(5);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();

		new Thread("Task in 1") {

			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				for (int i = 0; i < 10000; i++) {
					poolExecutor.execute(new Runnable() {
						@Override
						public void run() {
							try {
								TimeUnit.MILLISECONDS.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println(Thread.currentThread().getName() + " task run");
						}
					});
				}
			}
		}.start();

		new Thread("Task in 2") {

			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				for (int i = 0; i < 10000; i++) {
					poolExecutor.execute(new Runnable() {
						@Override
						public void run() {
							try {
								TimeUnit.MILLISECONDS.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println(Thread.currentThread().getName() + " task run");
						}
					});
				}
			}
		}.start();

		new Thread("Task in 3") {

			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				for (int i = 0; i < 10000; i++) {
					poolExecutor.execute(new Runnable() {
						@Override
						public void run() {
							try {
								TimeUnit.MILLISECONDS.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println(Thread.currentThread().getName() + " task run");
						}
					});
				}
			}
		}.start();

	}
}
