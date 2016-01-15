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
