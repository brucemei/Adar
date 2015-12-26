package free.adar.cdn.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiniuAPI {

private static final String AK = "5VgIFLBaRiovVudoXwWCBEUBfEzmvKKo_kdbcz7q";
	
	private static final String SK = "fK0nsUdg5oTOeTMqPtLdGw7RrrL3BorTDxA6rcFC";
	
	private static final String BUCKET = "adar";
	
	private static final String BUCKET_DOMAIN = "http://7xpgc9.com1.z0.glb.clouddn.com";
	
	private static final String SEPARATOR_SLASH = "/";
	
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
		buf.append(SEPARATOR_SLASH);
		buf.append(fileName);
		
		return buf.toString();
	}
}
