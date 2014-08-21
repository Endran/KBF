package nl.endran.showcase.database.sqllite.queries;

import nl.endran.showcase.database.sqllite.implementation.TableHelper;
import nl.endran.showcase.database.sqllite.queries.helpers.Query;
import nl.endran.showcase.database.sqllite.queries.helpers.QueryContainer;

public class GetAllUserBeersQuery implements Query {

	private final int userId;

	public GetAllUserBeersQuery() {
		this(-1);
	}

	public GetAllUserBeersQuery(final int userId) {
		this.userId = userId;
	}

	@Override
	public QueryContainer getQuery() {
		final QueryContainer queryContainer = new QueryContainer();

		queryContainer.table = TableHelper.TABLE_USER_BEER.NAME;
		queryContainer.projection = new String[] { TableHelper.TABLE_USER_BEER.COLUMN_ID, TableHelper.TABLE_USER_BEER.COLUMN_USER_ID,
				TableHelper.TABLE_USER_BEER.COLUMN_USER_NAME, TableHelper.TABLE_USER_BEER.COLUMN_BEER_ID, TableHelper.TABLE_USER_BEER.COLUMN_RATING,
				TableHelper.TABLE_USER_BEER.COLUMN_FAVORITE, TableHelper.TABLE_USER_BEER.COLUMN_WISHLIST, TableHelper.TABLE_USER_BEER.COLUMN_NOTE,
				TableHelper.TABLE_USER_BEER.COLUMN_UPDATED_SERVER, TableHelper.TABLE_USER_BEER.COLUMN_DATE };

		if (userId != 0) {
			queryContainer.selection = TableHelper.TABLE_USER_BEER.COLUMN_USER_ID + "=?";
			queryContainer.selectionArgs = new String[] { String.valueOf(userId) };
		}

		queryContainer.sortOrder = TableHelper.TABLE_USER_BEER.COLUMN_BEER_ID + " ASC";

		return queryContainer;
	}
}
