package nl.endran.database;

import java.util.HashMap;

import android.net.Uri;

public class Query {
	public Uri uri;
	public int uriId;
	public String table;
	public String[] projection;
	public String selection;
	public String[] selectionArgs;
	public String groupBy;
	public String having;
	public String sortOrder;
	public HashMap<String, String> columnMap;
}
