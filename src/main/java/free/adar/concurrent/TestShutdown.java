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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试
 * 		提交死循环任务到线程池, 通过ShutdownNow无法成功终止任务
 * 
 * 		ShutdownNow: 对线程池内所有工作线程发起中断
 * 
 * 		故:
 * 			提交到线程池任务应正确响应中断
 */
public class TestShutdown {

	public static void main(String[] args) {
		Runnable task = new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					System.out.println("Task running");
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		threadPool.execute(task);
		threadPool.execute(task);
		
		threadPool.shutdownNow();
	}
}
