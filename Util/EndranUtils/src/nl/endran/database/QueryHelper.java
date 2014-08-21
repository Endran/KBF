package nl.endran.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class QueryHelper {

	/**
	 * This method will concatenate all columnNames in the following fashion:<br>
	 * 'columnNames[0], columnNames[1], columnNames[2], ....,columnNames[n] '
	 */
	public static String columnToSelect(final String... columnNames) {
		final StringBuilder sb = new StringBuilder();
		sb.append(columnNames[0]);
		for (int i = 1; i < columnNames.length; i++) {
			sb.append(",").append(" ").append(columnNames[i]);
		}
		return sb.append(" ").toString();
	}

	/**
	 * Convenience funtion for INNER JOIN. Subsequent INNER JOIN's, must be
	 * changed using innerJoinContinued.
	 * 
	 * @param tableLeft
	 * @param tableRight
	 * @param propertyLeft
	 * @param propertyRight
	 * @param identifierLeft
	 * @param identifierRight
	 * @return
	 */
	public static String innerJoin(final String tableLeft, final String tableRight, final String propertyLeft,
			final String propertyRight, final String identifierLeft, final String identifierRight) {
		final StringBuilder sb = new StringBuilder();

		final String result = sb.append(tableLeft).append(" ").append(identifierLeft).append(" INNER JOIN ")
				.append(tableRight).append(" ").append(identifierRight).append(" ON ").append(identifierLeft)
				.append(".").append(propertyLeft).append("=").append(identifierRight).append(".").append(propertyRight)
				.append(" ").toString();

		return result;
	}

	/**
	 * Chaining function for chaining INNER JOIN's. Must be preceded by either a
	 * call to innerJoinContinued, or innerJoin.
	 * 
	 * @param identifierLeft
	 * @param tableRight
	 * @param propertyLeft
	 * @param propertyRight
	 * @param identifierRight
	 * @return
	 */
	public static String innerJoinContinued(final String identifierLeft, final String tableRight,
			final String propertyLeft, final String propertyRight, final String identifierRight) {
		final StringBuilder sb = new StringBuilder();

		final String result = sb.append("INNER JOIN ").append(tableRight).append(" ").append(identifierRight)
				.append(" ON ").append(identifierLeft).append(".").append(propertyLeft).append("=")
				.append(identifierRight).append(".").append(propertyRight).append(" ").toString();

		return result;
	}

	public static Cursor doQuery(final SQLiteOpenHelper dataBaseHandler, final Query query) {
		Cursor cursor = null;
		final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		queryBuilder.setTables(query.table);
		if (query.columnMap != null) {
			queryBuilder.setProjectionMap(query.columnMap);
		}

		SQLiteDatabase sqLiteDatabase = null;

		try {
			sqLiteDatabase = dataBaseHandler.getReadableDatabase();
			cursor = queryBuilder.query(sqLiteDatabase, query.projection, query.selection, query.selectionArgs,
					query.groupBy, query.having, query.sortOrder);
		} catch (final Exception ex) {
			if (cursor != null) {
				cursor.close();
			}
			cursor = null;
		} finally {
			// if (sqLiteDatabase != null) {
			// sqLiteDatabase.close();
			// }
		}
		return cursor;
	}
}
