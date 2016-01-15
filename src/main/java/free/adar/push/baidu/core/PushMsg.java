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
package free.adar.push.baidu.core;

import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;

import free.adar.push.baidu.message.MessageBuilder;
import free.adar.push.common.Device;

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
