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
package pers.adar.queue.blockqueue;

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
