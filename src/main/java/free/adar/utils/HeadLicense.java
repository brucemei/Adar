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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 许可声明 批量添加工具
 */
public class HeadLicense {

	private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

	private static final String FILE_SUFFIX = ".java";
	
	private static final String LICENSE;
	
	private static final String LICENSE_MARK;
	
	private static final int LICENSE_MARK_LENGTH = 20;
	
	private static final String CRLF = "\r\n";

	static {
		StringBuilder buf = new StringBuilder();
		buf.append("/**" + CRLF);
		buf.append(" * Copyright (c) 2015, adar.w (adar.w@outlook.com) " + CRLF);
		buf.append(" * " + CRLF);
		buf.append(" * http://www.adar-w.me" + CRLF);
		buf.append(" * " + CRLF);
		buf.append(" * Licensed under the Apache License, Version 2.0 (the \"License\");" + CRLF);
		buf.append(" * you may not use this file except in compliance with the License." + CRLF);
		buf.append(" * You may obtain a copy of the License at" + CRLF);
		buf.append(" * " + CRLF);
		buf.append(" * http://www.apache.org/licenses/LICENSE-2.0" + CRLF);
		buf.append(" * " + CRLF);
		buf.append(" * Unless required by applicable law or agreed to in writing, software" + CRLF);
		buf.append(" * distributed under the License is distributed on an \"AS IS\" BASIS," + CRLF);
		buf.append(" * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied." + CRLF);
		buf.append(" * See the License for the specific language governing permissions and" + CRLF);
		buf.append(" * limitations under the License." + CRLF);
		buf.append(" */" + CRLF);
		
		LICENSE = buf.toString();
		LICENSE_MARK = LICENSE.substring(0, LICENSE_MARK_LENGTH);
	}
	
	public static void main(String[] args) throws IOException {
		addHeadLicense("E:/Servers/Repository_Git/Adar/src");
	}
	
	private static void addHeadLicense(String dir) throws IOException {
		Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(final Path file, BasicFileAttributes attrs) throws IOException {
				if (file.toFile().getName().endsWith(FILE_SUFFIX)) {
					EXECUTOR_SERVICE.submit(() -> {
						try {
							addHeadLicense(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				}
				
				
				return FileVisitResult.CONTINUE;
			}
		});
		
		EXECUTOR_SERVICE.shutdown();
	}
	
	private static void addHeadLicense(Path file) throws IOException {
		ByteBuffer buffer = readIfNoHeadLicense(file);
		if (buffer == null) {
			return;
		}
		
		truncateAndWrite(file, headLicenseBuffer(buffer));
	}
	
	private static ByteBuffer readIfNoHeadLicense(Path file) throws IOException {
		try (FileChannel fileChannel = FileChannel.open(file, StandardOpenOption.READ)) {
			ByteBuffer buffer = ByteBuffer.allocate(Long.valueOf(file.toFile().length()).intValue());
			fileChannel.read(buffer);
			
			return checkHasHeadLicense(buffer) ? null : buffer;
		}
	}
	
	private static void truncateAndWrite(Path file, ByteBuffer buffer) throws IOException {
		try (FileChannel fileChannel = FileChannel.open(file, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
			fileChannel.write(buffer);
		}
	}
	
	private static ByteBuffer headLicenseBuffer(ByteBuffer buffer) {
		ByteBuffer bufferWithLicense = ByteBuffer.allocate(buffer.capacity() + LICENSE.length());
		buffer.flip();
		bufferWithLicense.put(LICENSE.getBytes()).put(buffer);
		bufferWithLicense.flip();
		
		return bufferWithLicense;
	}
	
	private static boolean checkHasHeadLicense(ByteBuffer buffer) {
		return new String(buffer.array(), 0, LICENSE_MARK_LENGTH).equals(LICENSE_MARK);
	}
}
