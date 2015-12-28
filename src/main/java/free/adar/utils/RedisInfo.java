package free.adar.utils;


import java.util.List;
import java.util.Set;

import redis.clients.jedis.DebugParams;
import redis.clients.jedis.Jedis;

/**
 * 测试Redis使用情况
 */
public class RedisInfo{
	
	public static void main(String[] args) {
		printRedisInfo("localhost", 6379, null);
	}
	
	/**
	 * 测试Redis使用情况
	 *
	 * @param host
	 * @param port
	 * @param auth
	 */
	public static void printRedisInfo(String host, int port, String auth) {
		Jedis client = null;
		try {
			client = new Jedis(host, port, 10000);
			if (auth != null) {
				client.auth(auth);
			}

			printRedisInfo(client);
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}
	
	/*
	 * 打印Redis使用信息: 
	 */
	private static void printRedisInfo(Jedis client) {
		System.out.println("*********************** RedisInfo ***********************\r\n");
		System.out.println("database" + "\t\t" + "type" + "\t\t" + "size" + "\t\t\t\t" + "key");
		
		Integer totalKey = 0;
		Long totalSize = 0L;
		
		List<String> databasesConf = client.configGet("databases");
		Integer databases = Integer.valueOf(databasesConf.get(1));
		for (int i = 0; i < databases; i++) {
			client.select(i);
			
			Set<String> keys = client.keys("*");
			for (String key : keys) {
				String type = client.type(key);
				
				Long len = null;
				if ("list".equals(type)) {
					len = client.llen(key);
				} else if ("set".equals(type)) {
					len = client.scard(key);
				} else if ("zset".equals(type)) {
					len = client.zcard(key);
				}
				type = len == null? type : type + "(" + len + ")";
				
				Long size = keySize(client, key);
				int keySize = key.getBytes().length;
				
				totalKey += 1;
				totalSize = totalSize + size + keySize;

				System.out.println(toPrint(String.valueOf(i), 20) + toPrint(type, 20) + toPrint(keySize + " --> " + size, 35) + toPrint(key, 30));
			}
		}
		
		System.out.println();
		System.out.println("totalKey: " + totalKey + "  totalSize: " + totalSize + "  totalMem(OS): " + memoryInfo(client));
	}
	
	/*
	 * 获取指定Key占用内存
	 */
	private static Long keySize(Jedis client, String key) {
		String fullInfo = client.debug(DebugParams.OBJECT(key));
		
		String sizeStr = fullInfo.substring(fullInfo.indexOf("serializedlength") + "serializedlength".length() + 1, fullInfo.indexOf("lru"));
		
		return Long.parseLong(sizeStr.trim());
	}
	
	/*
	 * 内存使用情况
	 */
	private static Long memoryInfo(Jedis client) {
		String fullInfo = client.info("memory");
		
		String sSubStr = "used_memory_rss:";
		String eSubStr = "used_memory_peak";
		String osMem = fullInfo.substring(fullInfo.indexOf(sSubStr) + sSubStr.length(), fullInfo.indexOf(eSubStr));
		
		return Long.parseLong(osMem.trim());
	}
	
	private static String toPrint(String str, int length) {
		int blankCount = length - str.length();
		String result = str;
		while (blankCount > 0) {
			result = result + " ";
			--blankCount;
		}
		
		return result;
	}
}