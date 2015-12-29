package free.adar.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static long interval(long startNanoTime) {
		return (System.nanoTime() - startNanoTime) / 1000000;
	}
	
	public static String currentTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
}
