package free.adar.nio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * 文件拷贝效率对比  
 * 		Buffer IO
 * 		NIO map 
 * 		NIO channel
 */
public class FileCopyPK {
	
	public static void main(String[] args) throws Exception {
		String src = "E:/File_install/jprofiler_windows-x64_8_0_7.exe";
		String out = "E:/outFile_";
		
		long start = System.currentTimeMillis();
		copyBufferIo(src, out);
		System.out.println("BufferIo: " + (System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		copyFileMap(src, out);
		System.out.println("FileMap: " + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		copyChannel(src, out);
		System.out.println("Channel: " + (System.currentTimeMillis() - start));
	}
	
	/**
	 * Buffer IO
	 * 
	 * @param src
	 * @param out
	 * @throws Exception
	 */
	private static void copyBufferIo(String src, String out) throws Exception {
		out = out + "BufferIo";
		
		InputStream srcFile = new BufferedInputStream(new FileInputStream(src));
		OutputStream outFile = new BufferedOutputStream(new FileOutputStream(out));
		
		int len;
		byte[] buf = new byte[1024 * 1024];
		while ((len = srcFile.read(buf)) != -1) {
			outFile.write(buf, 0, len);
			outFile.flush();
		}
		
		srcFile.close();
		outFile.close();
	}
	
	/**
	 * NIO Map
	 * 
	 * @param src
	 * @param out
	 * @throws Exception
	 */
	private static void copyFileMap(String src, String out) throws Exception {
		out = out + "FileMap";
		
		RandomAccessFile srcFile = new RandomAccessFile(src, "r");
		RandomAccessFile outFile = new RandomAccessFile(out, "rw");
		FileChannel srcChannel = srcFile.getChannel();
		FileChannel outChannel = outFile.getChannel();
		
		MappedByteBuffer srcfileMap = srcChannel.map(MapMode.READ_ONLY, 0, srcChannel.size());
		MappedByteBuffer outFileMap = outChannel.map(MapMode.READ_WRITE, 0, srcChannel.size());
		
		outFileMap.put(srcfileMap);
		
		srcChannel.close();
		outChannel.close();
		
		srcFile.close();
		outFile.close();
	}
	
	/**
	 * NIO channel
	 * 
	 * @param src
	 * @param out
	 * @throws Exception
	 */
	private static void copyChannel(String src, String out) throws Exception {
		out = out + "Channel";
		
		RandomAccessFile srcFile = new RandomAccessFile(src, "r");
		RandomAccessFile outFile = new RandomAccessFile(out, "rw");
		FileChannel srcChannel = srcFile.getChannel();
		FileChannel outChannel = outFile.getChannel();
		
		ByteBuffer buff = ByteBuffer.allocate(1024 * 1024);
		
		while (srcChannel.read(buff) != -1) {
			buff.flip();
			outChannel.write(buff);
			buff.clear();
		}
		
		srcChannel.close();
		outChannel.close();
		
		srcFile.close();
		outFile.close();
	}
}
