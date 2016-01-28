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
package pers.adar.pool.commonpool;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Common-pool2
 */
public class CommonPool {

	public static void main(String[] args) throws NoSuchElementException, IllegalStateException, Exception {
		GenericObjectPool<Book> objectPool = new GenericObjectPool<Book>(new BookPoolFactory(), buildPoolConfig(5, 10, 2));
		prestartCorePool(objectPool);

		List<Book> books = new LinkedList<Book>();
		for (int i = 0; i < 10; i++) {
			books.add(objectPool.borrowObject());
		}
		
		for (Book book : books) {
			objectPool.returnObject(book);
		}
	}
	
	private static GenericObjectPoolConfig buildPoolConfig(int corePoolSize, int maxPoolSize, int keepAliveTime) {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(corePoolSize);
		config.setMaxTotal(maxPoolSize);
		config.setMaxWaitMillis(keepAliveTime);
		
		return config;
	}
	
	private static void prestartCorePool(GenericObjectPool<?> objectPool) throws Exception {
		for (int i = 0; i < objectPool.getMaxIdle(); i++) {
			objectPool.addObject();
		}
	}
}

class BookPoolFactory implements PooledObjectFactory<Book> {
	
	private static AtomicInteger idGen = new AtomicInteger(0);

	/*
	 * borrowObject:
	 * 		1. makeObject(if pool empty)
	 * 		2. activateObject
	 * 		3. borrow
	 * 
	 * returnObject:
	 * 		1. passivateObject
	 * 		2. return
	 * 
	 * 		ex: if not core poolobj, will destroy when expire.
	 */
	
	/*
	 * Create New
	 */
	@Override
	public PooledObject<Book> makeObject() throws Exception {
		System.out.println("MK obj");
		return new DefaultPooledObject<Book>(new Book(idGen.addAndGet(1), "demo"));
	}

	/*
	 * 验证 
	 */
	@Override
	public boolean validateObject(PooledObject<Book> p) {
		System.out.println("YZ obj");
		return true;
	}
	
	/*
	 * Destroy 到期触发
	 */
	@Override
	public void destroyObject(PooledObject<Book> p) throws Exception {
		System.out.println("XH obj");
	}

	/*
	 * 激活
	 */
	@Override
	public void activateObject(PooledObject<Book> p) throws Exception {
		System.out.println("JH obj");
		
	}

	/*
	 * 钝化
	 */
	@Override
	public void passivateObject(PooledObject<Book> p) throws Exception {
		System.out.println("DH obj");
	}
}

class Book {
	
	private int id;
	
	private String name;

	public Book(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return super.toString() + " [id=" + id + ", name=" + name + "]";
	}
}
