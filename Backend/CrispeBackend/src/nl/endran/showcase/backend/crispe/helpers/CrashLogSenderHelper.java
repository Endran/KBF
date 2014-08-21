package nl.endran.showcase.backend.crispe.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.endran.installation.Installation;
import nl.endran.showcasebackend.R;
import nl.endran.storage.Storage;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

public class CrashLogSenderHelper {

	private final Context context;

	public final static String crashLogFile = "CrashLog.txt";
	public final static String beginOfException = "beginOfException";
	public final static String endOfException = "endOfException";
	public final static String nextItem = "DefaultUncaughtExceptionHandlerNextItem";

	public CrashLogSenderHelper(final Context context) {
		this.context = context;
	}

	public void send() {
		final String serverUrl = (String) context.getText(R.string.server_url);
		final String crashLogSuffix = (String) context.getText(R.string.server_crashlog);

		// Create a new HttpClient and Post Header
		final HttpClient httpclient = new DefaultHttpClient();
		final HttpPost httppost = new HttpPost(serverUrl + crashLogSuffix);

		final ArrayList<CrashLog> crashLogList = getCrashLogs();

		if (crashLogList.size() <= 0) {
			return;
		}

		try {
			final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			final String installID = Installation.getId(context);

			for (int i = 0; i < crashLogList.size(); i++) {
				final CrashLog crashLog = crashLogList.get(i);
				nameValuePairs.add(new BasicNameValuePair("installid[" + i + "]", "" + installID));
				nameValuePairs.add(new BasicNameValuePair("threadname[" + i + "]", "" + crashLog.threadName));
				nameValuePairs.add(new BasicNameValuePair("message[" + i + "]", "" + crashLog.exceptionMessage));
				nameValuePairs.add(new BasicNameValuePair("stacktrace[" + i + "]", "" + crashLog.exceptionTrace));
			}

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			httpclient.execute(httppost);

			final Storage storage = new Storage(context);
			storage.deleteFile(crashLogFile);

		} catch (final ClientProtocolException ex) {
		} catch (final IOException ex) {
		}
		return;
	}

	private ArrayList<CrashLog> getCrashLogs() {
		final Storage storage = new Storage(context);

		final ArrayList<CrashLog> result = new ArrayList<CrashLog>();

		try {
			final String crashLogText = storage.read(crashLogFile);
			final String[] crashLogTextArray = crashLogText.split(beginOfException);
			for (final String singleCrash : crashLogTextArray) {
				if (!singleCrash.contains(endOfException)) {
					continue;
				}

				final String[] crashLogItems = crashLogText.split(nextItem);
				final CrashLog crashLog = new CrashLog();
				crashLog.threadName = crashLogItems[1];
				crashLog.exceptionMessage = crashLogItems[2];
				crashLog.exceptionTrace = crashLogItems[3];
				result.add(crashLog);
			}
		} catch (final Exception ex) {
		}
		return result;
	}

	private class CrashLog {
		public String threadName;
		public String exceptionMessage;
		public String exceptionTrace;
	}

}
