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
package free.adar.cdn.qiniu;

import java.io.File;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiniuAPI {

	private static final String AK = "5VgIFLBaRiovVudoXwWCBEUBfEzmvKKo_kdbcz7q";
	
	private static final String SK = "fK0nsUdg5oTOeTMqPtLdGw7RrrL3BorTDxA6rcFC";
	
	private static final String BUCKET = "adar";
	
	private static final String BUCKET_DOMAIN = "http://7xpgc9.com1.z0.glb.clouddn.com";
	
	// Unit (s)
	private static final long EXPIRED = 1 * 60;
	
	/**
	 * File Upload
	 */
	public static void upload(String fileName, byte[] fileBytes) throws QiniuException {
		String uploadToken = auth().uploadToken(BUCKET, null, EXPIRED, null);
		new UploadManager().put(fileBytes, fileName, uploadToken);
	}
	
	/**
	 * File Download URL
	 */
	public static String download(String fileName) {
		return auth().privateDownloadUrl(buildDownloadUrl(fileName), EXPIRED);
	}
	
	/**
	 * File Delete
	 */
	public static void delete(String fileName) throws QiniuException {
		new BucketManager(auth()).delete(BUCKET, fileName);
	}
	
	private static Auth auth() {
		return Auth.create(AK, SK);
	}
	
	private static String buildDownloadUrl(String fileName) {
		StringBuilder buf = new StringBuilder();
		buf.append(BUCKET_DOMAIN);
		buf.append(File.separator);
		buf.append(fileName);
		
		return buf.toString();
	}
}
