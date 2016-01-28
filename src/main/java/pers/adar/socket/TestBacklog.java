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
package pers.adar.socket;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 检测当前环境Backlog上限
 */
public class TestBacklog {

	private static final int PORT = 10080;

	private static final int BACKLOG = 20480;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		new ServerSocket(PORT, BACKLOG);
		
		boolean fullOver = false;
		for (int i = 1; i <= BACKLOG; i++) {
			try {
				System.out.println("Check socket count: " + i);
				
				Socket socket = new Socket();
				socket.connect(new InetSocketAddress("localhost", PORT));
			} catch (ConnectException e) {
				System.out.println("Current environment upper limit: " + i);
				
				break;
			}
			
			if (i == BACKLOG) {
				fullOver = true;
			}
		}
		
		if (fullOver) {
			System.out.println("Current environment upper limit more than " + BACKLOG);
		}
	}
}
