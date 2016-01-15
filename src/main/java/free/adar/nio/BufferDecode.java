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

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class BufferDecode {
	
	private static final String UTF8 = "UTF-8";
	
	private static final String PATH = "E:/1.txt";
	
	public static void main(String[] args) throws IOException {
		System.out.println(decode(ByteBuffer.wrap("hahaha哈哈".getBytes()), false));

		System.out.println(decode(readBuffer(PATH, false), true));

		System.out.println(decode(readBuffer(PATH, true), true));
	}
	
	private static String decode(ByteBuffer byteBuffer, boolean needFlip) throws IOException {
		if (needFlip) {
			byteBuffer.flip();
		}
		
		return Charset.forName(UTF8).decode(byteBuffer).toString();
	}

	private static ByteBuffer readBuffer(String path, boolean isDirect) throws IOException {
		RandomAccessFile file = new RandomAccessFile(path, "r");
		
		ByteBuffer buffer = null;
		if (isDirect) {
			buffer = ByteBuffer.allocateDirect(Long.valueOf(file.length()).intValue());
		} else {
			buffer = ByteBuffer.allocate(Long.valueOf(file.length()).intValue());
		}
		
		FileChannel srcChannel = file.getChannel();
		srcChannel.read(buffer);
		file.close();
		
		return buffer;
	}
}
