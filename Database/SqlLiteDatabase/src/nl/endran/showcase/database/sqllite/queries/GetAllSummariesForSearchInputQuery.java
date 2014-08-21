package nl.endran.showcase.database.sqllite.queries;

import java.util.HashMap;

import nl.endran.showcase.database.sqllite.implementation.TableHelper;
import nl.endran.showcase.database.sqllite.queries.helpers.Query;
import nl.endran.showcase.database.sqllite.queries.helpers.QueryContainer;
import nl.endran.showcase.database.sqllite.queries.helpers.QueryHelper;
import android.text.TextUtils;

public class GetAllSummariesForSearchInputQuery implements Query {

	private final String searchInput;

	public GetAllSummariesForSearchInputQuery(final String searchInput) {
		this.searchInput = searchInput;
	}

	@Override
	public QueryContainer getQuery() {
		final QueryContainer queryContainer = new QueryContainer();

		int identifierCounter = 0;
		final String identifier_FESTIVAL_BEER = "t" + identifierCounter++;
		final String identifier_TABLE_PAYMENT = "t" + identifierCounter++;
		final String identifier_TABLE_STOCK = "t" + identifierCounter++;
		final String identifier_TABLE_BEER = "t" + identifierCounter++;
		final String identifier_TABLE_BREWER = "t" + identifierCounter++;
		final String identifier_METHOD_ID = "t" + identifierCounter++;

		queryContainer.table = TextUtils.concat(
				QueryHelper.innerJoin(TableHelper.TABLE_FESTIVAL_BEER.NAME, TableHelper.TABLE_PAYMENT.NAME, TableHelper.TABLE_FESTIVAL_BEER.COLUMN_PAYMENT_ID,
						TableHelper.TABLE_PAYMENT.COLUMN_REMOTE_ID, identifier_FESTIVAL_BEER, identifier_TABLE_PAYMENT),
				QueryHelper.innerJoinContinued(identifier_FESTIVAL_BEER, TableHelper.TABLE_STOCK.NAME, TableHelper.TABLE_FESTIVAL_BEER.COLUMN_STOCK_ID,
						TableHelper.TABLE_STOCK.COLUMN_REMOTE_ID, identifier_TABLE_STOCK),
				QueryHelper.innerJoinContinued(identifier_FESTIVAL_BEER, TableHelper.TABLE_BEER.NAME, TableHelper.TABLE_FESTIVAL_BEER.COLUMN_BEER_ID,
						TableHelper.TABLE_BEER.COLUMN_REMOTE_ID, identifier_TABLE_BEER),
				QueryHelper.innerJoinContinued(identifier_TABLE_BEER, TableHelper.TABLE_BREWER.NAME, TableHelper.TABLE_BEER.COLUMN_BREWER_ID,
						TableHelper.TABLE_BREWER.COLUMN_REMOTE_ID, identifier_TABLE_BREWER),
				QueryHelper.innerJoinContinued(identifier_TABLE_BEER, TableHelper.TABLE_METHOD.NAME, TableHelper.TABLE_BEER.COLUMN_METHOD_ID,
						TableHelper.TABLE_METHOD.COLUMN_REMOTE_ID, identifier_METHOD_ID)).toString();

		queryContainer.projection = new String[] { TableHelper.TABLE_FESTIVAL_BEER.COLUMN_ID, TableHelper.TABLE_BEER.COLUMN_NAME,
				TableHelper.TABLE_BEER.COLUMN_PERCENTAGE, TableHelper.TABLE_BEER.COLUMN_RATING_COUNT, TableHelper.TABLE_BEER.COLUMN_RATING_AVERAGE,
				TableHelper.TABLE_BEER.COLUMN_COLOR, TableHelper.TABLE_BREWER.COLUMN_NAME, TableHelper.TABLE_BREWER.COLUMN_LOCATION,
				TableHelper.TABLE_BREWER.COLUMN_URL, TableHelper.TABLE_METHOD.COLUMN_NAME, TableHelper.TABLE_STOCK.COLUMN_NAME,
				TableHelper.TABLE_PAYMENT.COLUMN_NAME, TableHelper.TABLE_FESTIVAL_BEER.COLUMN_DESCRIPTION, TableHelper.TABLE_FESTIVAL_BEER.COLUMN_REMOTE_ID,
				TableHelper.TABLE_FESTIVAL_BEER.COLUMN_BEER_ID, TableHelper.TABLE_FESTIVAL_BEER.COLUMN_NUMBER, TableHelper.TABLE_FESTIVAL_BEER.COLUMN_PRICE,
				TableHelper.TABLE_FESTIVAL_BEER.COLUMN_AWARD, TableHelper.TABLE_FESTIVAL_BEER.COLUMN_FIRSTTIME };

		queryContainer.columnMap = new HashMap<String, String>();

		for (final String element : queryContainer.projection) {
			if (element.equals("_id")) {
				queryContainer.columnMap.put(element, identifier_FESTIVAL_BEER + "._id");
			} else {
				queryContainer.columnMap.put(element, element);
			}
		}

		// final String likeString = "%" + String.valueOf(searchInput) + "%";
		// queryContainer.selection = TableHelper.TABLE_FESTIVAL_BEER.COLUMN_DESCRIPTION + " LIKE ? OR" + TableHelper.TABLE_BEER.COLUMN_NAME + " LIKE ? OR"
		// + TableHelper.TABLE_BREWER.COLUMN_NAME + " LIKE ?";
		// queryContainer.selectionArgs = new String[] { likeString, likeString, likeString };

		final String likeString = "%" + String.valueOf(searchInput) + "%";
		queryContainer.selection = TableHelper.TABLE_FESTIVAL_BEER.COLUMN_DESCRIPTION + " LIKE ?";
		queryContainer.selectionArgs = new String[] { likeString };

		return queryContainer;
	}

}
