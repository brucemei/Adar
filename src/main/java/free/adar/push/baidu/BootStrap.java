package free.adar.push.baidu;

import free.adar.push.baidu.common.Device;
import free.adar.push.baidu.core.Push;
import free.adar.push.baidu.core.PushMsg;

public class BootStrap {
	 
	private static final String CHANNELID = "4053776551826423861";

	public static void main(String[] args) throws Exception {
		Push.pushMsgToSingleDevice(PushMsg.buildSingleDeviceRequest(CHANNELID, Device.ANDROID));
	}
}
