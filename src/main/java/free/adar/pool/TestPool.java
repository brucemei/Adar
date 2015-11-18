package free.adar.pool;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
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

class FactoryDemo implements PooledObjectFactory<Demo> {
	
	private static AtomicInteger idGen = new AtomicInteger(0);

	@Override
	public PooledObject<Demo> makeObject() throws Exception {
		System.out.println("MK obj");
		return new DefaultPooledObject<Demo>(new Demo(idGen.addAndGet(1), "demo"));
	}

	@Override
	public void destroyObject(PooledObject<Demo> p) throws Exception {
		System.out.println("XH obj");
	}

	@Override
	public boolean validateObject(PooledObject<Demo> p) {
		System.out.println("YZ obj");
		return true;
	}

	@Override
	public void activateObject(PooledObject<Demo> p) throws Exception {
		System.out.println("JH obj");
		
	}

	@Override
	public void passivateObject(PooledObject<Demo> p) throws Exception {
		System.out.println("DH obj");
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
