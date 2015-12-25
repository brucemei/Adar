package free.adar.push;

import free.adar.push.notnoop.ApnsPush;

@SuppressWarnings("unused")
public class BootStrap {
	 
	private static final String CHANNELID_BAIDU = "4053776551826423861";
	
	private static final String CHANNELID_APNS = "302bf3806f48d3cc23434a0cc632c03cfa6ba6da11bdbad9d7485acb1aefd991";

	public static void main(String[] args) throws Exception {
//		BaiduPush.pushMsgToSingleDevice(PushMsg.buildSingleDeviceRequest(CHANNELID, Device.ANDROID));
		
		ApnsPush.push(CHANNELID_APNS, "7777777", "Hello Apns Push.");
	}
}
