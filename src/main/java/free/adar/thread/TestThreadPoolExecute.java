package free.adar.thread;

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
public class TestThreadPoolExecute {
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
