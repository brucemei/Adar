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
package pers.adar.socket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class TestInetAddress {
	
	public static void main(String[] args) throws SocketException, UnknownHostException {
		printAddress();
	}
	
	public static void printLocal() throws SocketException {
		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		
		while (networkInterfaces.hasMoreElements()) {
			NetworkInterface networkInterface = networkInterfaces.nextElement();
			System.out.println(networkInterface.getName());
			
			Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
			while (inetAddresses.hasMoreElements()) {
				InetAddress inetAddress = inetAddresses.nextElement();
				System.out.println("\t" + inetAddress.getHostAddress());
			}
		}
	}
	
	private static void printAddress() throws UnknownHostException {
		InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
		for (InetAddress address : addresses) {
			System.out.println(address);
		}
	}
}
