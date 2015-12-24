package free.adar.push.baidu.core;

import com.baidu.yun.push.model.QueryMsgStatusRequest;

import free.adar.push.baidu.common.Device;

public class PushStatus {
	
	public static QueryMsgStatusRequest buildMsgStatusRequest(String msgId, Device device) {
		QueryMsgStatusRequest msgStatusRequest = new QueryMsgStatusRequest();
		msgStatusRequest.addMsgId(msgId);
		msgStatusRequest.addDeviceType(device.code());
		
		return msgStatusRequest;
	}
}
