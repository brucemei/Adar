package free.adar.push.notnoop;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import free.adar.push.common.Constants;
import free.adar.utils.PathUtil;

public class ApnsPush {
	
	private static ApnsService apnsService;
	
	static {
		apnsService = APNS.newService().withCert(PathUtil.getAbsolutePath(Constants.PUSH_IOS_CERT_PATH), Constants.PUSH_IOS_CERT_PASSWORD)
										.withSandboxDestination()
										.withDelegate(ApnsDelegate.getInstance())
										.build();
	}
	
	public static void push(String token, String id, String body) {
		apnsService.push(token, APNS.newPayload().alertBody(body).alertAction(id).build());
	}
}
