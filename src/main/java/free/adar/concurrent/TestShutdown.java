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
