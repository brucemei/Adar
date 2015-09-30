package free.test;

import java.io.File;
import java.io.FileFilter;

public class TestFilter {
	
	public static void main(String[] args) {
		filterFile("D:/BaiduYun5.2.0", ".ico");
	}
	
	static void filterFile(final String dir, final String type) {
		File file = new File(dir);
		File[] listFiles = file.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(type);
			}
		});
		
		for (File f : listFiles) {
			System.out.println(f.getName());
		}
	}
}
