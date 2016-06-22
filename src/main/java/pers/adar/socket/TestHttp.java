/**
 * Copyright (c) 2015, adar.w (adar-w@outlook.com) 
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestHttp {

	private static final byte CR = (byte) '\r';

	private static final byte LF = (byte) '\n';

	private static final String CRLF = "\r\n";

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(10080);

		while (true) {
			try {
				Socket socket = serverSocket.accept();
				System.out.println("Client: " + socket.getInetAddress().getHostAddress() + CRLF);

				InputStream inputStream = socket.getInputStream();
				byte[] buf = new byte[1024];
				int count = inputStream.read(buf);

				ByteArrayOutputStream bro = new ByteArrayOutputStream();

				boolean requestLineEnd = false;
				for (int i = 0; i < count; i++) {
					byte b = buf[i];
					if (!requestLineEnd && b == CR && buf[i + 1] == LF) {
						System.out.println("RequestLine:" + CRLF + bro.toString() + CRLF);

						i++;
						bro.reset();
						requestLineEnd = true;

						continue;
					} else if (requestLineEnd && b == CR && buf[i + 1] == LF && buf[i + 2] == CR && buf[i + 3] == LF) {
						System.out.println("Headers:" + CRLF + bro.toString() + CRLF);

						i = i + 3;
						bro.reset();

						continue;
					}

					bro.write(b);
				}

				System.out.println("Contant:" + CRLF + bro.toString() + CRLF);

				socket.close();
			} catch (IOException e) {
				continue;
			}
		}
	}
}
