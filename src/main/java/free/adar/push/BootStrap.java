package free.adar.push;

import free.adar.push.common.Device;
import free.adar.push.core.Push;
import free.adar.push.core.PushMsg;

public class BootStrap {
	 
	private static final String CHANNELID = "4053776551826423861";

	public static void main(String[] args) throws Exception {
		Push.pushMsgToSingleDevice(PushMsg.buildSingleDeviceRequest(CHANNELID, Device.ANDROID));
	}
}
