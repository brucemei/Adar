/**
 * Copyright (c) 2015, adar.w (adar-w@outlook.com) 
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
package pers.adar.push;

import pers.adar.push.notnoop.ApnsPush;

@SuppressWarnings("unused")
public class BootStrap {
	 
	private static final String CHANNELID_BAIDU = "4053776551826423861";
	
	private static final String CHANNELID_APNS = "302bf3806f48d3cc23434a0cc632c03cfa6ba6da11bdbad9d7485acb1aefd991";

	public static void main(String[] args) throws Exception {
//		BaiduPush.pushMsgToSingleDevice(PushMsg.buildSingleDeviceRequest(CHANNELID, Device.ANDROID));
		
		ApnsPush.push(CHANNELID_APNS, "7777777", "Hello Apns Push.");
	}
}
