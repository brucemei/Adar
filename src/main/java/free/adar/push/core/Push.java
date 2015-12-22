package free.adar.push.core;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;

import free.adar.push.message.MessageBuilder;

/**
 * 基于Baidu Push
 */
public class Push {
	
	private static final String API_KEY = "G9i3USqlkGcV8xN43kZ5GWns";
	
	private static final String SECRET_KEY = "aQElj489UiY7UipYbjLZVIxBtNabQ10H";
	
	private static final PushKeyPair KEYPAIR;
	static {
		KEYPAIR = new PushKeyPair(API_KEY, SECRET_KEY);
	}

	private static final int MESSSAGE_TYPE = 1;
	
	private static final int MSG_EXPIRES = 3600;
	
	public static BaiduPushClient buildPushClient() {
		BaiduPushClient pushClient = new BaiduPushClient(KEYPAIR, BaiduPushConstants.CHANNEL_REST_URL);
		pushClient.setChannelLogHandler(logHandler());
		
		return pushClient;
	}
	
	public static PushMsgToSingleDeviceRequest buildSingleDeviceRequest(String channelId, Device device) {
		return buildSingleDeviceRequest(channelId, device, MessageBuilder.buildAndroidMessage());
	}
	
	public static PushMsgToSingleDeviceRequest buildSingleDeviceRequest(String channelId, Device device, String message) {
		PushMsgToSingleDeviceRequest singleDeviceRequest = new PushMsgToSingleDeviceRequest();
		singleDeviceRequest.addChannelId(channelId);
		singleDeviceRequest.addDeviceType(device.code());
		singleDeviceRequest.addMessage(message);

		singleDeviceRequest.addMsgExpires(MSG_EXPIRES);
		singleDeviceRequest.addMessageType(MESSSAGE_TYPE);
		
		return singleDeviceRequest;
	}
	
	private static YunLogHandler logHandler() {
		return new YunLogHandler() {
			
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println("Level: " + event.getLevel());
				System.out.println("Message: " + event.getMessage());
			}
		};
	}
}
