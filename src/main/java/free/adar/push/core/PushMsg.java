package free.adar.push.core;

import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;

import free.adar.push.common.Device;
import free.adar.push.message.MessageBuilder;

public class PushMsg {
	
	private static final int MESSSAGE_TYPE = 1;
	
	private static final int MSG_EXPIRES = 3600;

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
}
