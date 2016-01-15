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
package free.adar.concurrent;

/**
 * 其他线程可以看到synchronized代码块中对变量的修改
 */
public class TestSynchronized {

	private int a = 0;
//	private volatile int a = 0;
	
	private void m1() throws InterruptedException {
		synchronized (TestSynchronized.class) {
			a = 1;
			Thread.sleep(1000);

			a = 2;
			Thread.sleep(1000);

			a = 3;
			Thread.sleep(1000);
		}
	}
	
	private void m2() {
		System.out.println(a);
	}
	
	public static void main(String[] args) {
		final TestSynchronized bean = new TestSynchronized();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (;;) {
					bean.m2();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "T-m2");

		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					bean.m1();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "T-m1");

		
		t1.start();
		t2.start();
	}
}
