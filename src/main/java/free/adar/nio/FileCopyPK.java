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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

/**
 * 文件拷贝效率对比  
 * 		Buffer IO
 * 		NIO map 
 * 		NIO channel
 * 		Files copy
 */
public class FileCopyPK {
	
	public static void main(String[] args) throws Exception {
		String src = "E:/File_install/Office_Professional_Plus_2013_64Bit.ISO";
//		String src = "E:/File_install/xshell_5.0.0655.exe";
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
		
		start = System.currentTimeMillis();
		copyFilesCopy(src, out);
		System.out.println("Files copy: " + (System.currentTimeMillis() - start));
	}
	
	/**
	 * Buffer IO
	 */
	private static void copyBufferIo(String src, String out) throws Exception {
		out = out + "BufferIo";
		
		try (InputStream srcFile = new BufferedInputStream(new FileInputStream(src));
			 OutputStream outFile = new BufferedOutputStream(new FileOutputStream(out))) {
			
			int len;
			byte[] buf = new byte[1024 * 1024];
			while ((len = srcFile.read(buf)) != -1) {
				outFile.write(buf, 0, len);
				outFile.flush();
			}
		}
	}
	
	/**
	 * NIO Map
	 */
	private static void copyFileMap(String src, String out) throws Exception {
		out = out + "FileMap";
		
		try (FileChannel srcChannel = FileChannel.open(Paths.get(src), StandardOpenOption.READ);
			 FileChannel outChannel = FileChannel.open(Paths.get(out), StandardOpenOption.CREATE, 
					 												   StandardOpenOption.READ,
					 												   StandardOpenOption.WRITE)) {
			
			MappedByteBuffer srcfileMap = srcChannel.map(MapMode.READ_ONLY, 0, srcChannel.size());
			MappedByteBuffer outFileMap = outChannel.map(MapMode.READ_WRITE, 0, srcChannel.size());
			
			outFileMap.put(srcfileMap);
		}
	}
	
	/**
	 * NIO channel
	 */
	private static void copyChannel(String src, String out) throws Exception {
		out = out + "Channel";
		
		try (FileChannel srcChannel = FileChannel.open(Paths.get(src), StandardOpenOption.READ);
			 FileChannel outChannel = FileChannel.open(Paths.get(out), StandardOpenOption.CREATE, 
					 												   StandardOpenOption.WRITE)) {
			ByteBuffer buff = ByteBuffer.allocate(1024 * 1024);
			while (srcChannel.read(buff) != -1) {
				buff.flip();
				outChannel.write(buff);
				buff.clear();
			}
		}
	}

	/**
	 * Files copy
	 */
	private static void copyFilesCopy(String src, String out) throws Exception {
		out = out + "Files";
		
		Path srcPath = Paths.get(src);
		Path outPath = Paths.get(out);
		
		Files.copy(srcPath, outPath, StandardCopyOption.REPLACE_EXISTING);
	}
}
