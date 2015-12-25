package free.adar.push.notnoop;

import com.notnoop.apns.ApnsDelegateAdapter;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.DeliveryError;

public class ApnsDelegate extends ApnsDelegateAdapter {
	
	private static final ApnsDelegate APNS_DELEGATE = new ApnsDelegate();
	
	private ApnsDelegate() {
		
	}
	
	public static ApnsDelegate getInstance() {
		return APNS_DELEGATE;
	}
	
	public void messageSent(final ApnsNotification message, final boolean resent) {
		System.out.println("Sent message " + message + " Resent: " + resent);
	}
	
	public void messageSendFailed(final ApnsNotification message, final Throwable e) {
		System.out.println("Failed message " + message);
	}
	
	public void connectionClosed(final DeliveryError e, final int messageIdentifier) {
		System.out.println("Closed connection: " + messageIdentifier + "\n   deliveryError " + e.toString());
	}
	
	public void cacheLengthExceeded(final int newCacheLength) {
		System.out.println("CacheLengthExceeded " + newCacheLength);
	}
	
	public void notificationsResent(final int resendCount) {
		System.out.println("NotificationResent " + resendCount);
	}
}
