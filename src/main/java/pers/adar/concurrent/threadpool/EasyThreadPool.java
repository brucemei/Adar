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
package pers.adar.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

public class EasyThreadPool implements ThreadPool {
	
	private static final int DEFAULT_POOL_SIZE = Runtime.getRuntime().availableProcessors();
	
	private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
	
	private final List<Worker> workers = new CopyOnWriteArrayList<Worker>(new ArrayList<Worker>(DEFAULT_POOL_SIZE));
	
	private final ThreadFactory threadFactory = new EasyThreadFactory();

	private int size;

	public EasyThreadPool(int size) {
		this.size = size < 1 ? DEFAULT_POOL_SIZE : size;
		
		prestartThread();
	}

	private void prestartThread() {
		for (int i = 0; i < size; i++) {
			newPoolThread();
		}
	}
	
	private void newPoolThread() {
		Worker worker = new Worker();
		Thread thread = threadFactory.newThread(worker);
		worker.setThread(thread);
		
		workers.add(worker);
		
		worker.start();
	}
	
	@Override
	public boolean execute(Runnable task) {
		return taskQueue.offer(task);
	}

	@Override
	public void shutdown() {
		for (Worker worker : workers) {
			worker.shutdown();
		}
	}
	
	class Worker implements Runnable {

		private Thread thread;
		
		private volatile boolean running = true;
		
		public void start() {
			thread.start();
		}
		
		public void setThread(Thread thread) {
			this.thread = thread;
		}

		@Override
		public void run() {
			while (running) {
				Runnable task = null;
				try {
					 task = taskQueue.take();
				} catch (InterruptedException e) {
					return;
				}
				
				if (task != null) {
					try {
						task.run();
					} catch (Exception e) {
						// Task exceptrion
						e.printStackTrace();
						
						newPoolThread();
					}
				}
			}
		}
		
		public void shutdown() {
			running = false;
			
			thread.interrupt();
		}
	}
}
