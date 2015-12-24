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
