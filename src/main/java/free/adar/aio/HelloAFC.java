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
		
		System.out.println("1: " + Thread.currentThread() + " " + System.currentTimeMillis());
		afc.read(buffer, 0, null, new CompletionHandler<Integer, Object>() {

			@Override
			public void completed(Integer result, Object attachment) {
				System.out.println("2: " + Thread.currentThread() + " " + System.currentTimeMillis());
				System.out.println("Completed: " + result);
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				System.out.println("Failed");
			}
		});
		System.out.println("3: " + Thread.currentThread() + " " + System.currentTimeMillis());
		
		Thread.sleep(2000);
	}
}
