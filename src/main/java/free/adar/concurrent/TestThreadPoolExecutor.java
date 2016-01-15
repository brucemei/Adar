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

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor
 * 	execute:
 * 		1. 检查是否到核心线程数
 * 		2. 放入任务到任务队列
 * 		3. 队列满则向最大线程扩充
 * 		4. RejectedExecutionHandler
 * 
 *  @see java.util.concurrent.ThreadPoolExecutor#execute(Runnable)
 */
public class TestThreadPoolExecutor {
	public static void main(String[] args) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(100000);
				} catch (InterruptedException e) {
					// Do nothing.
				}
			}
		};
		
		executor.execute(runnable);
		executor.execute(runnable);
		executor.execute(runnable);
		
		System.out.println(executor.getQueue().size() == 1 ? "先进任务队列" : "先向最大线程数扩充");
		
		executor.shutdownNow();
	}
}
