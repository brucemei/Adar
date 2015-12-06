package free.adar.pool;

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
public class TestPool {

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
	 * 		ex: if no core poolobj, will destroy when expire.
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
