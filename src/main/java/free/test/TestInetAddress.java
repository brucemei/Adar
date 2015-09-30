package free.test;

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
