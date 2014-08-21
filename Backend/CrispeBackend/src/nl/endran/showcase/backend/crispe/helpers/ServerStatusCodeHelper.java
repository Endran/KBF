package nl.endran.showcase.backend.crispe.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class ServerStatusCodeHelper {
	public static int getStatusCode(final HttpResponse response) throws IllegalStateException, IOException {
		if (response == null) {
			return -1;
		}
		if (response.getStatusLine() == null) {
			return -1;
		}
		return response.getStatusLine().getStatusCode();
	}

	public static String parseResponse(final HttpResponse response) throws IllegalStateException, IOException {
		if (response == null) {
			return "";
		}

		final HttpEntity entity = response.getEntity();
		if (entity == null) {
			return "";
		}

		final InputStream inputStream = entity.getContent();
		if (inputStream == null) {
			return "";
		}

		final ByteArrayOutputStream content = new ByteArrayOutputStream();

		// Read response into a buffered stream
		int readBytes = 0;
		final byte[] sBuffer = new byte[512];
		while ((readBytes = inputStream.read(sBuffer)) != -1) {
			content.write(sBuffer, 0, readBytes);
		}

		// Return result from buffered stream
		final String dataAsString = new String(content.toByteArray());
		return dataAsString;
	}
}
