package nl.endran.showcase.backend.crispe.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import nl.endran.storage.Storage;
import android.content.Context;

public class DownloadHelper {
	private static ILogger log = LoggerFactory.GetLoggerWithString(DownloadHelper.class.getSimpleName());

	/**
	 * Download content from the serverUrl and tries to save it to the provided
	 * filename.
	 * 
	 * @param context
	 *            Context
	 * @param filename
	 *            location to store the downloaded content
	 * @param serverUrl
	 *            URL of the server
	 * @return true if content is successfully downloaded, false otherwise.
	 */
	public static boolean downloadToFile(final Context context, final String filename, final String serverUrl) {
		boolean success = false;
		try {
			final URL url = new URL(serverUrl);
			final HttpURLConnection con = (HttpURLConnection) url.openConnection();
			final InputStream inputStream = con.getInputStream();
			final StringBuilder stringBuilder = readStream(inputStream);
			final Storage storage = new Storage(context);
			storage.save(filename, stringBuilder.toString());
			success = true;
		} catch (final IOException ex) {
			log.error("downloadToFile IOException", ex);
		}

		return success;
	}

	public static StringBuilder readStream(final InputStream in) throws IOException {
		final StringBuilder stringBuilder = new StringBuilder();

		IOException exception = null;

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line.trim());
			}
		} catch (final IOException ex) {
			log.error("readStream IOException1", ex);
			exception = ex;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (final IOException ex) {
					log.error("readStream IOException2", ex);
					if (exception == null) {
						exception = ex;
					}
				}
			}
		}

		if (exception != null) {
			throw exception;
		}

		return stringBuilder;
	}
}
