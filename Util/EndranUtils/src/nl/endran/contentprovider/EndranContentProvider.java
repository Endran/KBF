package nl.endran.contentprovider;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class EndranContentProvider extends ContentProvider {
	protected ILogger log = LoggerFactory.GetLogger(this);

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		log.verbose("delete");
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		log.verbose("getType");
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		log.verbose("insert");
		return null;
	}

	@Override
	public boolean onCreate() {
		initializeLogger();
		log = LoggerFactory.GetLogger(this);
		log.verbose("Logger acquired in EndranActivity");
		
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		log.verbose("query");
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		log.verbose("update");
		return 0;
	}

	/**
	 * Override this in the first/main activity. Initialize the logger,
	 * otherwise the loglevel is set to OFF and the logtag to
	 * UninitializedLogTag.
	 */
	protected void initializeLogger() {
	}
}