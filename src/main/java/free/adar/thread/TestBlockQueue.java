package free.adar.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestBlockQueue {

	public static final BlockingQueue<String> task_queue = new LinkedBlockingQueue<String>();

	private static Long runSecond = 15L;

	public static void main(String[] args) {
		// 生产者
		new Thread(new Producer(), "producer-1").start();
		new Thread(new Producer(), "producer-2").start();
		new Thread(new Producer(), "producer-3").start();
		new Thread(new Producer(), "producer-4").start();

		// 消费者
		new Thread(new Consumer(), "consumer-1").start();
		new Thread(new Consumer(), "consumer-2").start();

		/*
		 * 每5秒打印任务队列状态
		 */
		Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				synchronized (this) {
					System.out.println("************************************************************");
					System.out.println("Task_Queue: " + task_queue);
					System.out.println("************************************************************");
				}
			}
		}, 0, 5, TimeUnit.SECONDS);

		/*
		 * 程序退出时打印 任务队列 剩余任务
		 */
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				System.out.println(TestBlockQueue.task_queue);
			}
		});

		/*
		 * 运行指定时间
		 */
		try {
			Thread.sleep(runSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.exit(0);
	}
}

class Producer implements Runnable {

	private static AtomicInteger taskId = new AtomicInteger(1);

	/**
	 * 生产任务
	 */
	@Override
	public void run() {
		while (true) {
			int id = taskId.getAndIncrement();
			try {
				Thread.sleep(1000);

				TestBlockQueue.task_queue.put("Task-" + id);
				System.out.println("[" + Thread.currentThread().getName() + "]" + "Producer Task: " + "task-" + id);
			} catch (InterruptedException e) {
				System.out.println("生产任务失败   id: " + id);
				e.printStackTrace();

				continue;
			}
		}
	}
}

class Consumer implements Runnable {

	/**
	 * 消费任务
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);

				String task = TestBlockQueue.task_queue.take();
				System.out.println("[" + Thread.currentThread().getName() + "]" + "Consumer Task: " + task);
			} catch (InterruptedException e) {
				System.out.println("消费任务失败");
				e.printStackTrace();

				continue;
			}
		}
	}
}
