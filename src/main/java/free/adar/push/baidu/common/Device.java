package free.adar.push.baidu.common;

public enum Device {

	ANDROID(3), 
	
	IOS(4);
	
	private int code;
	
	private Device(int code) {
		this.code = code;
	}
	
	public int code() {
		return code;
	}
}