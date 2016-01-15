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
package free.adar.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@SuppressWarnings("unused")
public class TestSocket {
	
	public static void main(String[] args) {
		testSocketBindWrongHost();
	}

	/**
	 * 测试绑定错误的IP地址
	 */
	@SuppressWarnings("resource")
	private static void testSocketBindWrongHost() {
		try {
			Socket socket = new Socket();
			socket.bind(new InetSocketAddress("250.1.23.1", 10086));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试Socket超时
	 */
	private static void testSocketTimeout() {
		Socket socket = null;
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress("localhost", 80), 1000);
//			socket.connect(new InetSocketAddress("localhost", 8080), 1000);
			
			System.out.println("Connect success.");
		} catch (IOException e) {
			System.out.println("Connect timeout.");
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {}
			}
		}
	}
}
