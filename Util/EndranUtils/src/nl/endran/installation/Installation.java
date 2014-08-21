package nl.endran.installation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Installation {
	private static final ILogger log = LoggerFactory.GetLogger(Installation.class.getSimpleName());

	private static String sID = null;
	public static final String INSTALLATION = "INSTALLATION";

	public synchronized static String getId(Context context) {
		if (sID == null || sID.equals("")) {
			try {
				final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context
						.getApplicationContext());
				sID = sharedPrefs.getString(INSTALLATION, "");

				if (sID == null || sID.equals("")) {
					File installation = new File(context.getFilesDir(), INSTALLATION);
					context.deleteFile(INSTALLATION);
					writeInstallationFile(installation);
					sID = readInstallationFile(installation);
					context.deleteFile(INSTALLATION);
					sharedPrefs.edit().putString(INSTALLATION, sID).commit();
				}
			} catch (Exception ex) {
				log.error("getId Exception occurred", ex);
			}
		}
		return sID;
	}

	public synchronized static boolean isFirstTime(Context context) {
		final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context
				.getApplicationContext());
		sID = sharedPrefs.getString(INSTALLATION, "");
		boolean firstTime = (sID == null || sID.equals(""));
		return firstTime;
	}

	private static String readInstallationFile(File installation) throws IOException {
		RandomAccessFile f = new RandomAccessFile(installation, "r");
		byte[] bytes = new byte[(int) f.length()];
		f.readFully(bytes);
		f.close();
		return new String(bytes);
	}

	private static void writeInstallationFile(File installation) throws IOException {
		FileOutputStream out = new FileOutputStream(installation);
		String id = UUID.randomUUID().toString();
		out.write(id.getBytes());
		out.close();
	}
}
