package free.adar.concurrent.threadpool;

public interface ThreadPool {
	
	boolean execute(Runnable task);
	
	void shutdown();
	
}
