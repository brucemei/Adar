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
package pers.adar.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestFuture {
	
	private static final int CODE_TIME = 4;
	
	private static final int TIME_OUT_TIME = 3;

	public static void main(String[] args) throws Exception {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<?> future = executor.submit(new Callable<Object>() {
			
			@Override
			public Object call() throws InterruptedException {
				TimeUnit.SECONDS.sleep(CODE_TIME);
				
				return null;
			}
		});
		executor.shutdown();
		
		try {
			future.get(TIME_OUT_TIME, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			System.out.println("Time out");
		}
	}
}
