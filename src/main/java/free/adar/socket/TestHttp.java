package free.adar.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestHttp {

	private static final byte CR = (byte) '\r';

	private static final byte LF = (byte) '\n';

	private static final String CRLF = "\r\n";

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(10080);

		while (true) {
			try {
				Socket socket = serverSocket.accept();
				System.out.println("Client: " + socket.getInetAddress().getHostAddress() + CRLF);

				InputStream inputStream = socket.getInputStream();
				byte[] buf = new byte[1024];
				int count = inputStream.read(buf);

				ByteArrayOutputStream bro = new ByteArrayOutputStream();

				boolean requestLineEnd = false;
				for (int i = 0; i < count; i++) {
					byte b = buf[i];
					if (!requestLineEnd && b == CR && buf[i + 1] == LF) {
						System.out.println("RequestLine:" + CRLF + bro.toString() + CRLF);

						i++;
						bro.reset();
						requestLineEnd = true;

						continue;
					} else if (requestLineEnd && b == CR && buf[i + 1] == LF && buf[i + 2] == CR && buf[i + 3] == LF) {
						System.out.println("Headers:" + CRLF + bro.toString() + CRLF);

						i = i + 3;
						bro.reset();

						continue;
					}

					bro.write(b);
				}

				System.out.println("Contant:" + CRLF + bro.toString() + CRLF);

				socket.close();
			} catch (IOException e) {
				continue;
			}
		}
	}
}
