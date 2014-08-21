package nl.endran.showcase.database.sqllite.implementation;

import android.content.ContentValues;

import nl.endran.showcaselib.datacontainers.Beer;
import nl.endran.showcaselib.datacontainers.Brewer;
import nl.endran.showcaselib.datacontainers.Festival;
import nl.endran.showcaselib.datacontainers.FestivalBeer;
import nl.endran.showcaselib.datacontainers.Method;
import nl.endran.showcaselib.datacontainers.Payment;
import nl.endran.showcaselib.datacontainers.Stock;
import nl.endran.showcaselib.datacontainers.UserBeer;

public class ContentValuesHelper {

	public static ContentValues getContentValues(final Object object) {
		if (object instanceof Beer) {
			return getContentValues((Beer) object);
		} else if (object instanceof FestivalBeer) {
			return getContentValues((FestivalBeer) object);
		} else if (object instanceof UserBeer) {
			return getContentValues((UserBeer) object);
		} else if (object instanceof Brewer) {
			return getContentValues((Brewer) object);
		} else if (object instanceof Method) {
			return getContentValues((Method) object);
		} else if (object instanceof Payment) {
			return getContentValues((Payment) object);
		} else if (object instanceof Stock) {
			return getContentValues((Stock) object);
		} else if (object instanceof Festival) {
			return getContentValues((Festival) object);
		} else if (object instanceof Application) {
			return getContentValues((Application) object);
		}
		return new ContentValues();
	}

	public static ContentValues getContentValues(final Application application) {
		final ContentValues values = new ContentValues();
		values.put(TableHelper.TABLE_APPLICATION.COLUMN_REMOTE_ID, application.getId());
		values.put(TableHelper.TABLE_APPLICATION.COLUMN_TIMESTAMP, application.getTimestamp());
		return values;
	}

	public static ContentValues getContentValues(final FestivalBeer festivalBeer) {
		final ContentValues values = new ContentValues();
		values.put(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_REMOTE_ID, festivalBeer.getId());
		if (festivalBeer.isFestivalIDSet()) {
			values.put(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_FESTIVAL_ID, festivalBeer.getFestivalID());
		}
		if (festivalBeer.isBeerIDSet()) {
			values.put(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_BEER_ID, festivalBeer.getBeerID());
		}
		if (festivalBeer.isNumberSet()) {
			values.put(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_NUMBER, festivalBeer.getNumber());
		}
		if (festivalBeer.isPaymentIDSet()) {
			values.put(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_PAYMENT_ID, festivalBeer.getPaymentID());
		}
		if (festivalBeer.isPriceSet()) {
			values.put(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_PRICE, festivalBeer.getPrice());
		}
		if (festivalBeer.isAwardSet()) {
			values.put(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_AWARD, festivalBeer.getAward());
		}
		if (festivalBeer.isFirstTimeSet()) {
			values.put(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_FIRSTTIME, festivalBeer.isFirstTime());
		}
		if (festivalBeer.isStockIDSet()) {
			values.put(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_STOCK_ID, festivalBeer.getStockID());
		}
		if (festivalBeer.isDescriptionSet()) {
			values.put(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_DESCRIPTION, festivalBeer.getDescription());
		}
		if (festivalBeer.isImageIDSet()) {
			values.put(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_IMAGE_ID, festivalBeer.getImageID());
		}
		if (festivalBeer.isUpdatedServerSet()) {
			values.put(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_UPDATED_SERVER, festivalBeer.isUpdatedServer());
		}
		return values;
	}

	public static ContentValues getContentValues(final Festival festival) {
		final ContentValues values = new ContentValues();
		values.put(TableHelper.TABLE_FESTIVAL.COLUMN_REMOTE_ID, festival.getId());

		if (festival.isNameSet()) {
			values.put(TableHelper.TABLE_FESTIVAL.COLUMN_NAME, festival.getName());
		}
		if (festival.isDateSet()) {
			values.put(TableHelper.TABLE_FESTIVAL.COLUMN_DATE, festival.getDate());
		}
		if (festival.isLocationSet()) {
			values.put(TableHelper.TABLE_FESTIVAL.COLUMN_LOCATION, festival.getLocation());
		}
		if (festival.isDescriptionSet()) {
			values.put(TableHelper.TABLE_FESTIVAL.COLUMN_DESCRIPTION, festival.getDescription());
		}
		return values;
	}

	public static ContentValues getContentValues(final Method method) {
		final ContentValues values = new ContentValues();
		values.put(TableHelper.TABLE_METHOD.COLUMN_REMOTE_ID, method.getId());
		if (method.isNameSet()) {
			values.put(TableHelper.TABLE_METHOD.COLUMN_NAME, method.getName());
		}
		return values;
	}

	public static ContentValues getContentValues(final Payment payment) {
		final ContentValues values = new ContentValues();
		values.put(TableHelper.TABLE_PAYMENT.COLUMN_REMOTE_ID, payment.getId());
		if (payment.isNameSet()) {
			values.put(TableHelper.TABLE_PAYMENT.COLUMN_NAME, payment.getName());
		}
		return values;
	}

	public static ContentValues getContentValues(final Stock stock) {
		final ContentValues values = new ContentValues();
		values.put(TableHelper.TABLE_STOCK.COLUMN_REMOTE_ID, stock.getId());
		if (stock.isNameSet()) {
			values.put(TableHelper.TABLE_STOCK.COLUMN_NAME, stock.getName());
		}
		return values;
	}

	public static ContentValues getContentValues(final UserBeer userBeer) {
		final ContentValues values = new ContentValues();
		values.put(TableHelper.TABLE_USER_BEER.COLUMN_BEER_ID, userBeer.getBeerId());
		values.put(TableHelper.TABLE_USER_BEER.COLUMN_USER_ID, userBeer.getUserId());
		if (userBeer.isFavoriteSet()) {
			values.put(TableHelper.TABLE_USER_BEER.COLUMN_FAVORITE, userBeer.getFavorite());
		}
		if (userBeer.isUserNameSet()) {
			values.put(TableHelper.TABLE_USER_BEER.COLUMN_USER_NAME, userBeer.getUserName());
		}
		if (userBeer.isDateSet()) {
			values.put(TableHelper.TABLE_USER_BEER.COLUMN_DATE, userBeer.getDate());
		}
		if (userBeer.isRatingSet()) {
			values.put(TableHelper.TABLE_USER_BEER.COLUMN_RATING, userBeer.getRating());
		}
		if (userBeer.isWishlistSet()) {
			values.put(TableHelper.TABLE_USER_BEER.COLUMN_WISHLIST, userBeer.getWishlist());
		}
		if (userBeer.isNoteSet()) {
			values.put(TableHelper.TABLE_USER_BEER.COLUMN_NOTE, userBeer.getNote());
		}
		if (userBeer.isUpdatedServerSet()) {
			values.put(TableHelper.TABLE_USER_BEER.COLUMN_UPDATED_SERVER, userBeer.isUpdatedServer());
		}
		return values;
	}

	public static ContentValues getContentValues(final Beer beer) {
		final ContentValues values = new ContentValues();
		values.put(TableHelper.TABLE_BEER.COLUMN_REMOTE_ID, beer.getId());
		if (beer.isNameSet()) {
			values.put(TableHelper.TABLE_BEER.COLUMN_NAME, beer.getName());
		}
		if (beer.isColorSet()) {
			values.put(TableHelper.TABLE_BEER.COLUMN_COLOR, beer.getColor());
		}
		if (beer.isBrewerIDSet()) {
			values.put(TableHelper.TABLE_BEER.COLUMN_BREWER_ID, beer.getBrewerID());
		}
		if (beer.isMethodIDSet()) {
			values.put(TableHelper.TABLE_BEER.COLUMN_METHOD_ID, beer.getMethodID());
		}
		if (beer.isPercentageSet()) {
			values.put(TableHelper.TABLE_BEER.COLUMN_PERCENTAGE, beer.getPercentage());
		}
		if (beer.isRatingCountSet()) {
			values.put(TableHelper.TABLE_BEER.COLUMN_RATING_COUNT, beer.getRatingCount());
		}
		if (beer.isRatingAvgSet()) {
			values.put(TableHelper.TABLE_BEER.COLUMN_RATING_AVERAGE, beer.getRatingAvg());
		}
		return values;
	}

	public static ContentValues getContentValues(final Brewer brewer) {
		final ContentValues values = new ContentValues();
		values.put(TableHelper.TABLE_BREWER.COLUMN_REMOTE_ID, brewer.getId());
		if (brewer.isNameSet()) {
			values.put(TableHelper.TABLE_BREWER.COLUMN_NAME, brewer.getName());
		}
		if (brewer.isDescriptionSet()) {
			values.put(TableHelper.TABLE_BREWER.COLUMN_DESCRIPTION, brewer.getDescription());
		}
		if (brewer.isLocationSet()) {
			values.put(TableHelper.TABLE_BREWER.COLUMN_LOCATION, brewer.getLocation());
		}
		if (brewer.isUrlSet()) {
			values.put(TableHelper.TABLE_BREWER.COLUMN_URL, brewer.getUrl());
		}
		return values;
	}
}
