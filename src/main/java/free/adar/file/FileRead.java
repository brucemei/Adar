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
package free.adar.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @since 1.7
 */
public class FileRead {
	
	public static void main(String[] args) throws IOException {
		FileChannel fileChannel = FileChannel.open(Paths.get("E:/1.txt"), StandardOpenOption.READ);
		
		ByteBuffer buffer = ByteBuffer.allocate(10);
		
		fileChannel.read(buffer);
		while (!buffer.hasRemaining()) {
			ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
			
			buffer.flip();
			newBuffer.put(buffer);
			
			fileChannel.read(newBuffer);

			buffer = newBuffer;
		}
		
		buffer.flip();
		System.out.println(Charset.defaultCharset().decode(buffer).toString());
		
		fileChannel.close();
	}
}
