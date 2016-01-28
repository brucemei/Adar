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
package pers.adar.concurrent.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class EasyThreadFactory implements ThreadFactory {

	private static final String MARK = "EasyThreadPool-Worker-";
	
	private AtomicInteger id = new AtomicInteger();
	
	private boolean isDaemon = false;
	
	@Override
	public Thread newThread(Runnable runnable) {
		Thread thread = new Thread(runnable, MARK + id.incrementAndGet());
		thread.setDaemon(isDaemon);
		
		return thread;
	}
}
