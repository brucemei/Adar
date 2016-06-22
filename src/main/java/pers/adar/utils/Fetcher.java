/**
 * Copyright (c) 2016, adar.w (adar-w@outlook.com) 
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Fetcher {

	public static CdarBuilder connect(String url) {
		return new CdarBuilder(url);
	}
	
	public static class CdarBuilder {
		
		private static final HttpMethod DEFAULT_HTTPMETHOD = HttpMethod.GET;
		
		private static final int DEFAULT_TIMEOUT = 60 * 1000; 
		
		private String url;
		
		private HttpMethod method;
		
		private int timeout;
		
		private Map<String, String> headers = new HashMap<String, String>();

		private Map<String, Object> formdatas = new HashMap<String, Object>();

		private String body;
		
		public CdarBuilder(String url) {
			this.url = url;
			this.method = DEFAULT_HTTPMETHOD;
			this.timeout = DEFAULT_TIMEOUT;
		}
		
		public CdarBuilder method(HttpMethod method) {
			this.method = method;
			
			return this;
		}

		public CdarBuilder post() {
			method(HttpMethod.POST);
			
			return this;
		}
		
		public CdarBuilder timeout(int timeout) {
			this.timeout = timeout;
			
			return this;
		}
		
		public CdarBuilder header(String name, String value) {
			headers.put(name, value);
			
			return this;
		}
		
		public CdarBuilder formdata(String name, Object value){
			formdatas.put(name, value);

			return this;
		}
		
		public CdarBuilder body(String body) {
			this.body = body;
			
			return this;
		}

		public void download(String file) throws Exception {
			Files.copy(send(), Paths.get(file), StandardCopyOption.REPLACE_EXISTING);
		}
		
		public String text() throws Exception {
			InputStream inputStream = send();
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
			
			StringBuilder text = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				text.append(line);
				text.append("\r\n");
			}
			rd.close();
			
			return text.toString();
		}
		
		private InputStream send() throws Exception {
			HttpURLConnection httpURLConnection = url.startsWith("https") ? (HttpsURLConnection) new URL(url).openConnection() : (HttpURLConnection) new URL(url).openConnection();
			
			httpURLConnection.setReadTimeout(timeout);
			httpURLConnection.setConnectTimeout(timeout);
			
			httpURLConnection.setRequestMethod(method.getValue());

			for (Map.Entry<String, String> me : headers.entrySet()) {
				httpURLConnection.setRequestProperty(me.getKey(), me.getValue());
			}
			
			if (HttpMethod.POST == method) {
				httpURLConnection.setDoOutput(true);
				httpURLConnection.getOutputStream().write(buildBody().getBytes());
			}
			
			return httpURLConnection.getInputStream();
		}
		
		private String buildBody() {
			return body == null ? formatFormdata() : body;
		}
		
		private String formatFormdata() {
			StringBuilder buf = new StringBuilder();
			
			boolean isFir = true;
			for (Map.Entry<String, Object> me : formdatas.entrySet()) {
				if (!isFir) {
					buf.append("&");
				}
				
				buf.append(me.getKey() + "=" + me.getValue());
				
				isFir = false;
			}
			
			return buf.toString();
		}
	}
	
	private enum HttpMethod {

		GET("GET"),
		
		POST("POST");
		
		private String value;
		
		private HttpMethod(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
}

