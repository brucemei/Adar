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

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.baidu.yun.push.model.QueryMsgStatusRequest;
import com.baidu.yun.push.model.QueryMsgStatusResponse;

import free.adar.push.common.Constants;

public class BaiduPush {
	
	private static final PushKeyPair KEYPAIR;
	static {
		KEYPAIR = new PushKeyPair(Constants.API_KEY, Constants.SECRET_KEY);
	}

	public static BaiduPushClient buildPushClient() {
		return buildPushClient(false);
	}
	
	public static BaiduPushClient buildPushClient(boolean openLog) {
		BaiduPushClient pushClient = new BaiduPushClient(KEYPAIR, BaiduPushConstants.CHANNEL_REST_URL);
		if (openLog) {
			pushClient.setChannelLogHandler(logHandler());
		}
		
		return pushClient;
	}
	
	public static PushMsgToSingleDeviceResponse pushMsgToSingleDevice(PushMsgToSingleDeviceRequest singleDeviceRequest) throws PushClientException, PushServerException {
		return pushMsgToSingleDevice(singleDeviceRequest, false);
	}

	public static PushMsgToSingleDeviceResponse pushMsgToSingleDevice(PushMsgToSingleDeviceRequest singleDeviceRequest, boolean openLog) throws PushClientException, PushServerException {
		return pushMsgToSingleDevice(buildPushClient(openLog), singleDeviceRequest);
	}

	public static PushMsgToSingleDeviceResponse pushMsgToSingleDevice(BaiduPushClient pushClient, PushMsgToSingleDeviceRequest singleDeviceRequest) throws PushClientException, PushServerException {
		return pushClient.pushMsgToSingleDevice(singleDeviceRequest);
	}
	
	public static QueryMsgStatusResponse queryMsgStatus(QueryMsgStatusRequest msgStatusRequest) throws PushClientException, PushServerException {
		return queryMsgStatus(buildPushClient(), msgStatusRequest);
	}
	
	public static QueryMsgStatusResponse queryMsgStatus(BaiduPushClient pushClient, QueryMsgStatusRequest msgStatusRequest) throws PushClientException, PushServerException {
		return pushClient.queryMsgStatus(msgStatusRequest);
	}
	
	private static YunLogHandler logHandler() {
		return new YunLogHandler() {
			
			@Override
			public void onHandle(YunLogEvent event) {
				System.err.println(event.getLevel());
				System.err.println(event.getMessage());
			}
		};
	}
}
