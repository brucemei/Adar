package free.adar.push.core;

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

public class Push {
	
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
