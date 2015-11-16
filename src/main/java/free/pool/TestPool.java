package free.pool;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class TestPool {

	public static void main(String[] args) throws NoSuchElementException, IllegalStateException, Exception {
		ObjectPool<Demo> objectPool = new GenericObjectPool<Demo>(new FactoryDemo());
		
		for (int i = 0; i < 10; i++) {
			Demo demo = objectPool.borrowObject();
			System.out.println(demo);
			
			if (i % 2 == 1) {
				objectPool.returnObject(demo);
			}
		}
	}
}

class FactoryDemo extends BasePooledObjectFactory<Demo> {
	
	private static AtomicInteger idGen = new AtomicInteger(0);

	@Override
	public Demo create() throws Exception {
		return new Demo(idGen.addAndGet(1), "demo");
	}

	@Override
	public PooledObject<Demo> wrap(Demo obj) {
		return new DefaultPooledObject<Demo>(obj);
	}
}


class Demo {
	
	private int id;
	
	private String name;

	public Demo(int id, String name) {
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
