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
package free.adar.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 直接缓冲区与非直接缓冲区读取效率PK
 * 		效率对比不包含缓冲区创建时间(直接缓冲区创建开销较大)
 * 
 * 		耗时对比:
 * 			Read DirectBuffer < Read Buffer
 * 			Write Buffer < Write DirectBuffer
 * 
 * JVM: -Xms512m -Xmx2048m -XX:MaxDirectMemorySize=2048m
 */
public class DirectBufferPK {
	
	private static final String src = "E:/File_install/jdk-6u43-windows-x64.exe";
	
	private static final String out1 = "E:/1.txt";

	private static final String out2 = "E:/2.txt";
	
	private static final int size = 1024 * 1024 * 1024;
	
	public static void main(String[] args) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(size);
		ByteBuffer directBuffer = ByteBuffer.allocateDirect(size);
		
		long start = System.currentTimeMillis();
		bufferRead(src, buffer);
		System.out.println("Read Buffer: " + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		bufferRead(src, directBuffer);
		System.out.println("Read DirectBuffer: " + (System.currentTimeMillis() - start));

		buffer.flip();
		directBuffer.flip();
		
		start = System.currentTimeMillis();
		bufferWrite(out1, buffer);
		System.out.println("Write Buffer: " + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		bufferWrite(out2, directBuffer);
		System.out.println("Write DirectBuffer: " + (System.currentTimeMillis() - start));
	}
	
	private static void bufferRead(String src, ByteBuffer buffer) throws IOException {
		RandomAccessFile srcFile = new RandomAccessFile(src, "r");
		FileChannel srcChannel = srcFile.getChannel();
		
		srcChannel.read(buffer);
		
		srcFile.close();
	}
	
	private static void bufferWrite(String out, ByteBuffer buffer) throws IOException {
		File file = new File(out);
		if (!file.exists()) {
			file.createNewFile();
		}
		
		RandomAccessFile srcFile = new RandomAccessFile(out, "rw");
		FileChannel srcChannel = srcFile.getChannel();
		
		srcChannel.write(buffer);
		
		srcFile.close();
	}
}
