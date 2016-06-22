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
package pers.adar.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * @since jdk1.6
 */
public class HttpEchoServer {

	private static final char SPACE = ' ';
	
	private static final String HEADER_SEPARATOR = ": ";
	
	private static final String CRLF = "\r\n";
	
	private static final String CONTEXT_PATH = "/";
	
	public static void main(String[] args) throws IOException {
		HttpServer httpServer = HttpServer.create(new InetSocketAddress(10080), 100);
		httpServer.setExecutor(Executors.newCachedThreadPool());
		httpServer.createContext(CONTEXT_PATH, new HttpHandler() {
			
			@Override
			public void handle(HttpExchange exchange) throws IOException {
				StringBuilder buf = new StringBuilder();

				buf.append(exchange.getRequestMethod() + SPACE);
				buf.append(exchange.getRequestURI().toString() + SPACE);
				buf.append(exchange.getProtocol() + SPACE);
				buf.append(CRLF);

				for (Entry<String, List<String>> me : exchange.getRequestHeaders().entrySet()) {
					buf.append(me.getKey());
					buf.append(HEADER_SEPARATOR);
					
					List<String> values = me.getValue();
					for (int i = 0; i < values.size(); i++) {
						if (i != 0) {
							buf.append(SPACE);
						}
						buf.append(values.get(i));
					}
					
					buf.append(CRLF);
				}
				
				String response = buf.toString();
				exchange.sendResponseHeaders(200, response.getBytes().length);
				exchange.getResponseBody().write(response.getBytes());
				exchange.close();
			}
		});
		
		httpServer.start();
	}
}
