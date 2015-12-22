package free.adar.push;

import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;

import free.adar.push.core.Device;
import free.adar.push.core.Push;

public class BootStrap {
	 
	private static final String CHANNELID = "";

	public static void main(String[] args) throws Exception {
		BaiduPushClient pushClient = Push.buildPushClient();
		
		PushMsgToSingleDeviceRequest singleDeviceRequest = Push.buildSingleDeviceRequest(CHANNELID, Device.ANDROID);
		
		System.out.println("Push Time(B): " + System.currentTimeMillis());
		PushMsgToSingleDeviceResponse response = pushClient.pushMsgToSingleDevice(singleDeviceRequest);
		System.out.println("Push Time(A): " + System.currentTimeMillis());
		
		System.out.println("Msg id: " + response.getMsgId());
		System.out.println("SendTime: " + response.getSendTime());
	}
}
