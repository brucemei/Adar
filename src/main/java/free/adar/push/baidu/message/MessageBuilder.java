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
package free.adar.push.baidu.message;

import com.alibaba.fastjson.JSONObject;

public class MessageBuilder {
	
	private static final String KEY_TITLE = "title";
	
	private static final String KEY_DESC = "description";
	
	private static final String DEFAULT_TITLE = "Push";
	
	private static final String DEFAULT_DESC = "The Desc.";
	
	public static String buildAndroidMessage() {
		return buildAndroidMessage(DEFAULT_TITLE, DEFAULT_DESC);
	}
	
	public static String buildAndroidMessage(String title, String desc) {
		JSONObject message = new JSONObject();
		message.put(KEY_TITLE, title);
		message.put(KEY_DESC, desc);
		
		return message.toJSONString();
	}
	
}
