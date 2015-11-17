package free.ada.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 直接缓冲区与非直接缓冲区PK
 * 
 * JVM: -Xms512m -Xmx2048m -XX:MaxDirectMemorySize=2048m
 */
public class DirectBufferPK {
	
	private static final String src = "E:/File_install/jprofiler_windows-x64_8_0_7.exe";
	
	private static final int size = 1024 * 1024 * 1024;
	
	public static void main(String[] args) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(size);
		ByteBuffer directBuffer = ByteBuffer.allocateDirect(size);
		
		long start = System.currentTimeMillis();
		readToBuffer(src, buffer);
		System.out.println("Buffer: " + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		readToBuffer(src, directBuffer);
		System.out.println("DirectBuffer: " + (System.currentTimeMillis() - start));
	}
	
	private static void readToBuffer(String src, ByteBuffer buffer) throws IOException {
		RandomAccessFile srcFile = new RandomAccessFile(src, "r");
		FileChannel srcChannel = srcFile.getChannel();
		
		srcChannel.read(buffer);
		
		srcFile.close();
	}
}
