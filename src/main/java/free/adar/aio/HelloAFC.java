package free.adar.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.util.concurrent.Future;

public class HelloAFC {
	
	private static final String path = "E:/File_study/RFC2616（HTTP）中文版.pdf";
	
	private static final int DEFAUL_SIZE = 1024 * 1024 * 500;
	
	public static void main(String[] args) throws Exception {
		AsynchronousFileChannel afc = AsynchronousFileChannel.open(Paths.get(path));
		
		ByteBuffer buffer = ByteBuffer.allocate(DEFAUL_SIZE);
		
		processFuture(afc, buffer);
		
		processCompletionHandler(afc, buffer);

		afc.close();
	}

	private static void processFuture(AsynchronousFileChannel afc, ByteBuffer buffer) throws Exception {
		buffer.clear();
		
		Future<Integer> future = afc.read(buffer, 0);
		
		System.out.println("Completed: " + future.get());
	}

	private static void processCompletionHandler(AsynchronousFileChannel afc, ByteBuffer buffer) throws Exception {
		buffer.clear();
		
		afc.read(buffer, 0, null, new CompletionHandler<Integer, Object>() {

			@Override
			public void completed(Integer result, Object attachment) {
				System.out.println("Completed: " + result);
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				System.out.println("Failed");
			}
		});
	}
}
