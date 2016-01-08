package free.adar.queue.blockqueue;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 优先级队列
 * 
 * 1. 根据Comparable确定排序规则,compareTo返回值越小优先级越高
 * 2. 排序操作在放入队列时执行
 * 
 * @see java.util.concurrent.PriorityBlockingQueue<E>
 */
public class TestPriorityBlockingQueue {

	public static void main(String[] args) throws InterruptedException {
		Book book1 = new Book(1);
		Book book2 = new Book(2);
		Book book3 = new Book(3);
		Book book4 = new Book(4);
		
		PriorityBlockingQueue<Book> priorityBlockingQueue = new PriorityBlockingQueue<Book>();
		priorityBlockingQueue.put(book2);
		priorityBlockingQueue.put(book3);
		priorityBlockingQueue.put(book1);
		priorityBlockingQueue.put(book4);
		
		System.out.println(priorityBlockingQueue.take().getId());
		System.out.println(priorityBlockingQueue.take().getId());
		System.out.println(priorityBlockingQueue.take().getId());
		System.out.println(priorityBlockingQueue.take().getId());
	}
}

class Book implements Comparable<Book> {
	
	private int id;
	
	public Book(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public int compareTo(Book book) {
		System.out.println(id + " " + book.getId());
		return this.id - book.getId();
	}
}
