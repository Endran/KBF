package nl.endran.showcase.backend.crispe.helpers;

import java.util.ArrayList;
import java.util.List;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import nl.endran.showcase.backend.crispe.util.DataObjectParser;
import nl.endran.showcase.backend.crispe.util.DownloadHelper;
import nl.endran.showcasebackend.R;
import nl.endran.showcaselib.datacontainers.ShowcaseDataObject;
import android.content.Context;

public class SyncAllHelper {
	ILogger log = LoggerFactory.GetLogger(this);
	private final Context context;
	private final String timestamp;

	public SyncAllHelper(final Context context, final String timestamp) {
		this.context = context;
		this.timestamp = timestamp;
	}

	public boolean download() {
		boolean succes = true;

		final String serverUrl = (String) this.context.getText(R.string.server_url);
		final String festivalQuery = "?" + (String) this.context.getText(R.string.server_festival_query);
		final String languageQuery = "&" + (String) this.context.getText(R.string.server_language_query);
		String[] extensions;

		String timestampQuery = "";
		if (this.timestamp != null && !this.timestamp.equals("")) {
			timestampQuery = "&last=" + this.timestamp.replace(" ", "%20");
			extensions = this.context.getResources().getStringArray(R.array.server_extensions_lite);
		} else {
			extensions = this.context.getResources().getStringArray(R.array.server_extensions);
		}

		for (final String extension : extensions) {
			final String url = serverUrl + extension + festivalQuery + timestampQuery + languageQuery;
			final String filename = extension.replace("/", "").replace("_lite", "");
			succes &= DownloadHelper.downloadToFile(this.context, filename, url);
		}

		return succes;
	}

	public List<ArrayList<ShowcaseDataObject>> parse() {
		final boolean completeSyncOfDatabase = (this.timestamp == null || this.timestamp.length() <= 0);
		final DataObjectParser dataObjectParser = new DataObjectParser(this.context, completeSyncOfDatabase);
		return dataObjectParser.parse();
	}
}
