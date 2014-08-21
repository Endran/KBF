package nl.endran.showcase.database.sqllite.queries;

import nl.endran.showcase.database.sqllite.implementation.TableHelper;
import nl.endran.showcase.database.sqllite.queries.helpers.Query;
import nl.endran.showcase.database.sqllite.queries.helpers.QueryContainer;

public class GetBrewerQuery implements Query {

	private final int brewerId;

	public GetBrewerQuery() {
		this(-1);
	}

	public GetBrewerQuery(final int brewerId) {
		this.brewerId = brewerId;
	}

	@Override
	public QueryContainer getQuery() {
		final QueryContainer queryContainer = new QueryContainer();

		queryContainer.table = TableHelper.TABLE_BREWER.NAME;
		queryContainer.projection = new String[] { TableHelper.TABLE_BREWER.COLUMN_ID, TableHelper.TABLE_BREWER.COLUMN_REMOTE_ID,
				TableHelper.TABLE_BREWER.COLUMN_DESCRIPTION, TableHelper.TABLE_BREWER.COLUMN_LOCATION, TableHelper.TABLE_BREWER.COLUMN_NAME,
				TableHelper.TABLE_BREWER.COLUMN_URL };

			if (brewerId >= 0) {
				queryContainer.selection = TableHelper.TABLE_BREWER.COLUMN_REMOTE_ID + "=?";
				queryContainer.selectionArgs = new String[] { String.valueOf(brewerId) };
			}

		return queryContainer;
	}
}
