package nl.endran.showcase.database.sqllite.implementation;

import nl.endran.showcaselib.datacontainers.Beer;
import nl.endran.showcaselib.datacontainers.Brewer;
import nl.endran.showcaselib.datacontainers.Festival;
import nl.endran.showcaselib.datacontainers.FestivalBeer;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.datacontainers.UserBeer;
import android.database.Cursor;

public class CursorConverter {

	public static Application toApplication(final Cursor cursor) {
		Application application;
		application = new Application();
		int columnId = -1;

		columnId = cursor.getColumnIndex(TableHelper.TABLE_APPLICATION.COLUMN_TIMESTAMP);
		if (columnId >= 0) {
			application.setTimestamp(cursor.getString(columnId));
		}
		columnId = cursor.getColumnIndex(TableHelper.TABLE_APPLICATION.COLUMN_REMOTE_ID);
		if (columnId >= 0) {
			application.setId(cursor.getInt(columnId));
		}

		return application;
	}

	public static Beer toBeer(final Cursor cursor) {
		Beer beer;
		beer = new Beer();
		int columnId = -1;

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_BREWER_ID);
		if (columnId >= 0) {
			beer.setBrewerID(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_METHOD_ID);
		if (columnId >= 0) {
			beer.setMethodID(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_PERCENTAGE);
		if (columnId >= 0) {
			beer.setPercentage(cursor.getDouble(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_NAME);
		if (columnId >= 0) {
			beer.setName(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_RATING_COUNT);
		if (columnId >= 0) {
			beer.setRatingCount(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_RATING_AVERAGE);
		if (columnId >= 0) {
			beer.setRatingAvg(cursor.getDouble(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_COLOR);
		if (columnId >= 0) {
			beer.setColor(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_REMOTE_ID);
		if (columnId >= 0) {
			beer.setId(cursor.getInt(columnId));
		}

		return beer;
	}

	public static final Brewer toBrewer(final Cursor cursor) {
		Brewer brewer;
		brewer = new Brewer();
		int columnId = -1;

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BREWER.COLUMN_NAME);
		if (columnId >= 0) {
			brewer.setName(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BREWER.COLUMN_DESCRIPTION);
		if (columnId >= 0) {
			brewer.setDescription(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BREWER.COLUMN_LOCATION);
		if (columnId >= 0) {
			brewer.setLocation(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BREWER.COLUMN_REMOTE_ID);
		if (columnId >= 0) {
			brewer.setId(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BREWER.COLUMN_URL);
		if (columnId >= 0) {
			brewer.setUrl(cursor.getString(columnId));
		}

		return brewer;
	}

	public static final FestivalBeer toFestivalBeer(final Cursor cursor) {
		FestivalBeer festivalBeer;
		festivalBeer = new FestivalBeer();
		int columnId = -1;

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_AWARD);
		if (columnId >= 0) {
			festivalBeer.setAward(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_BEER_ID);
		if (columnId >= 0) {
			festivalBeer.setBeerID(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_DESCRIPTION);
		if (columnId >= 0) {
			festivalBeer.setDescription(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_FESTIVAL_ID);
		if (columnId >= 0) {
			festivalBeer.setFestivalID(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_FIRSTTIME);
		if (columnId >= 0) {
			festivalBeer.setFirstTime(cursor.getInt(columnId) == 1 ? true : false);
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_REMOTE_ID);
		if (columnId >= 0) {
			festivalBeer.setId(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_NUMBER);
		if (columnId >= 0) {
			festivalBeer.setNumber(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_PAYMENT_ID);
		if (columnId >= 0) {
			festivalBeer.setPaymentID(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_PRICE);
		if (columnId >= 0) {
			festivalBeer.setPrice(cursor.getDouble(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_STOCK_ID);
		if (columnId >= 0) {
			festivalBeer.setStockID(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_IMAGE_ID);
		if (columnId >= 0) {
			festivalBeer.setImageID(cursor.getInt(columnId));
		}

		return festivalBeer;
	}

	public static final Festival toFestival(final Cursor cursor) {
		Festival festival;
		festival = new Festival();
		int columnId = -1;

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL.COLUMN_NAME);
		if (columnId >= 0) {
			festival.setName(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL.COLUMN_DESCRIPTION);
		if (columnId >= 0) {
			festival.setDescription(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL.COLUMN_REMOTE_ID);
		if (columnId >= 0) {
			festival.setId(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL.COLUMN_LOCATION);
		if (columnId >= 0) {
			festival.setLocation(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL.COLUMN_DATE);
		if (columnId >= 0) {
			festival.setDate(cursor.getString(columnId));
		}

		return festival;
	}

	public static final UserBeer toUserBeer(final Cursor cursor) {
		UserBeer userBeer;
		userBeer = new UserBeer();
		int columnId = -1;

		columnId = cursor.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_BEER_ID);
		if (columnId >= 0) {
			userBeer.setBeerId(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_FAVORITE);
		if (columnId >= 0) {
			userBeer.setFavorite(cursor.getInt(columnId) == 1 ? true : false);
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_RATING);
		if (columnId >= 0) {
			userBeer.setRating(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_USER_ID);
		if (columnId >= 0) {
			userBeer.setUserId(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_WISHLIST);
		if (columnId >= 0) {
			userBeer.setWishlist(cursor.getInt(columnId) == 1 ? true : false);
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_NOTE);
		if (columnId >= 0) {
			userBeer.setNote(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_UPDATED_SERVER);
		if (columnId >= 0) {
			userBeer.setUpdatedServer(cursor.getInt(columnId) == 1 ? true : false);
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_USER_NAME);
		if (columnId >= 0) {
			userBeer.setUserName(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_DATE);
		if (columnId >= 0) {
			userBeer.setDate(cursor.getString(columnId));
		}

		return userBeer;
	}

	public static Summary toSummary(final Cursor cursor) {
		Summary summary;
		summary = new Summary();
		int columnId = -1;

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_BEER_ID);
		if (columnId >= 0) {
			summary.setBeerId(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_NAME);
		if (columnId >= 0) {
			summary.setBeerName(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_DESCRIPTION);
		if (columnId >= 0) {
			summary.setBeerDiscription(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_RATING_AVERAGE);
		if (columnId >= 0) {
			summary.setBeerRatingAvg(cursor.getDouble(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_COLOR);
		if (columnId >= 0) {
			summary.setBeerColor(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_RATING_COUNT);
		if (columnId >= 0) {
			summary.setBeerRatingCount(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_METHOD.COLUMN_NAME);
		if (columnId >= 0) {
			summary.setMethodName(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BREWER.COLUMN_NAME);
		if (columnId >= 0) {
			summary.setBrewerName(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BREWER.COLUMN_LOCATION);
		if (columnId >= 0) {
			summary.setBrewerLocation(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BREWER.COLUMN_URL);
		if (columnId >= 0) {
			summary.setBrewerUrl(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_REMOTE_ID);
		if (columnId >= 0) {
			summary.setFestivalBeerID(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_STOCK.COLUMN_NAME);
		if (columnId >= 0) {
			summary.setStockName(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_NUMBER);
		if (columnId >= 0) {
			summary.setFestivalBeerNumber(cursor.getInt(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_PRICE);
		if (columnId >= 0) {
			summary.setFestivalBeerPrice(cursor.getDouble(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_PERCENTAGE);
		if (columnId >= 0) {
			summary.setBeerPercentage(cursor.getDouble(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_PAYMENT.COLUMN_NAME);
		if (columnId >= 0) {
			summary.setPaymentName(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_AWARD);
		if (columnId >= 0) {
			summary.setFestivalBeerAward(cursor.getString(columnId));
		}

		columnId = cursor.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_FIRSTTIME);
		if (columnId >= 0) {
			summary.setFestivalBeerFirstTime(cursor.getInt(columnId) == 1 ? true : false);
		}

		return summary;
	}
}