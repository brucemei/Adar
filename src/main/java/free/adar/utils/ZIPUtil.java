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
package free.adar.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * ZIP解压
 */
public class ZIPUtil {
	
	private static final String SRC_PATH = "E:/YLNF.zip";

	private static final String OUT_PATH = "E:/YLNF";

	public static void main(String[] args) throws ZipException {
		ZipFile zipFile = new ZipFile(SRC_PATH);
		
		zipFile.extractAll(OUT_PATH);
	}
}
