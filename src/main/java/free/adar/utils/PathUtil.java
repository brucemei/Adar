package free.adar.utils;

import java.io.File;
import java.net.URISyntaxException;

public class PathUtil {
	
	public static String getAbsolutePath(String relativePath) {
		try {
			return new File(getClassLoader().getResource(relativePath).toURI()).getAbsolutePath();
		} catch (URISyntaxException e) {
			return null;
		}
	}
	
	private static ClassLoader getClassLoader() {
		return PathUtil.class.getClassLoader();
	}
}
