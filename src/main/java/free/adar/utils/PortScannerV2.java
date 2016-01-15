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
package free.adar.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 端口扫描 v2.0
 * 
 * 采用多线程并发扫描
 */
public class PortScannerV2 {

	/**
	 * 连接超时 ms
	 */
	private static final int TIME_OUT_CONNECT = 200;

	private static final int PROCESSORS = Runtime.getRuntime().availableProcessors() * 20;

	private static final ExecutorService executor = Executors.newFixedThreadPool(PROCESSORS);

	public static void main(String[] args) {
		scan("localhost", 8070, 10000);
	}

	/**
	 * Port scan
	 */
	private static void scan(String host, int start, int end) {
		if (start < 0 || start > end) {
			throw new IllegalArgumentException();
		}

		for (int port = start; port < end; port++) {
			executor.execute(new Scanner(host, port));
		}

		executor.shutdown();
	}

	static class Scanner implements Runnable {

		private String host;

		private int port;

		public Scanner(String host, int port) {
			super();
			this.host = host;
			this.port = port;
		}

		public void run() {
			Socket socket = new Socket();
			try {
				System.out.println("Scan port...   Port: " + port);

				socket.connect(new InetSocketAddress(host, port), TIME_OUT_CONNECT);
				System.err.println("Port is listen: " + port);
			} catch (UnknownHostException e) {
				System.out.println("Host wrong: " + host);
				System.exit(1);
			} catch (IOException e) {
				// do nothing
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}
}
