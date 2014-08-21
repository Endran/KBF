package nl.endran.showcase.database.sqllite.queries;

import nl.endran.showcase.database.sqllite.implementation.TableHelper;
import nl.endran.showcase.database.sqllite.queries.helpers.Query;
import nl.endran.showcase.database.sqllite.queries.helpers.QueryContainer;

public class GetFestivalQuery implements Query {

	private final int festivalId;

	public GetFestivalQuery() {
		this(-1);
	}

	public GetFestivalQuery(final int festivalId) {
		this.festivalId = festivalId;
	}

	@Override
	public QueryContainer getQuery() {
		final QueryContainer queryContainer = new QueryContainer();

		queryContainer.table = TableHelper.TABLE_FESTIVAL.NAME;
		queryContainer.projection = new String[] { TableHelper.TABLE_FESTIVAL.COLUMN_ID, TableHelper.TABLE_FESTIVAL.COLUMN_REMOTE_ID,
				TableHelper.TABLE_FESTIVAL.COLUMN_DATE, TableHelper.TABLE_FESTIVAL.COLUMN_DESCRIPTION, TableHelper.TABLE_FESTIVAL.COLUMN_LOCATION,
				TableHelper.TABLE_FESTIVAL.COLUMN_NAME };

		if (festivalId >= 0) {
			queryContainer.selection = TableHelper.TABLE_FESTIVAL.COLUMN_REMOTE_ID + "=?";
			queryContainer.selectionArgs = new String[] { String.valueOf(festivalId) };
		}

		return queryContainer;
	}
}
