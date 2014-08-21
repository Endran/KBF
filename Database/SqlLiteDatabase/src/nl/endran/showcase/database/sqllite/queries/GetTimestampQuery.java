package nl.endran.showcase.database.sqllite.queries;

import java.util.HashMap;

import nl.endran.showcase.database.sqllite.implementation.TableHelper;
import nl.endran.showcase.database.sqllite.queries.helpers.Query;
import nl.endran.showcase.database.sqllite.queries.helpers.QueryContainer;

public class GetTimestampQuery implements Query {
	public QueryContainer getQuery() {
		final QueryContainer queryContainer = new QueryContainer();

		queryContainer.table = TableHelper.TABLE_APPLICATION.NAME;

		queryContainer.projection = new String[] { TableHelper.TABLE_APPLICATION.COLUMN_ID, TableHelper.TABLE_APPLICATION.COLUMN_REMOTE_ID,
				TableHelper.TABLE_APPLICATION.COLUMN_TIMESTAMP };

		queryContainer.columnMap = new HashMap<String, String>();

		for (final String element : queryContainer.projection) {
			queryContainer.columnMap.put(element, element);
		}

		queryContainer.selection = null;
		queryContainer.selectionArgs = null;
		queryContainer.sortOrder = null;

		return queryContainer;
	}
}
