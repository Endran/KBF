package nl.endran.showcase.database.sqllite.implementation;

import android.database.DatabaseUtils.InsertHelper;

import nl.endran.showcaselib.datacontainers.Beer;
import nl.endran.showcaselib.datacontainers.Brewer;
import nl.endran.showcaselib.datacontainers.Festival;
import nl.endran.showcaselib.datacontainers.FestivalBeer;
import nl.endran.showcaselib.datacontainers.Method;
import nl.endran.showcaselib.datacontainers.Payment;
import nl.endran.showcaselib.datacontainers.Stock;
import nl.endran.showcaselib.datacontainers.UserBeer;

public class InsertHelperBindHelper {

	public static void bindToInsertHelper(final InsertHelper ih, final Object object) {
		if (object instanceof Beer) {
			bindToInsertHelper(ih, (Beer) object);
		} else if (object instanceof FestivalBeer) {
			bindToInsertHelper(ih, (FestivalBeer) object);
		} else if (object instanceof UserBeer) {
			bindToInsertHelper(ih, (UserBeer) object);
		} else if (object instanceof Brewer) {
			bindToInsertHelper(ih, (Brewer) object);
		} else if (object instanceof Method) {
			bindToInsertHelper(ih, (Method) object);
		} else if (object instanceof Payment) {
			bindToInsertHelper(ih, (Payment) object);
		} else if (object instanceof Stock) {
			bindToInsertHelper(ih, (Stock) object);
		} else if (object instanceof Festival) {
			bindToInsertHelper(ih, (Festival) object);
		} else if (object instanceof Application) {
			bindToInsertHelper(ih, (Application) object);
		}
	}

	public static void bindToInsertHelper(final InsertHelper ih, final Application application) {
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_APPLICATION.COLUMN_REMOTE_ID), application.getId());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_APPLICATION.COLUMN_TIMESTAMP), application.getTimestamp());
	}

	public static void bindToInsertHelper(final InsertHelper ih, final FestivalBeer festivalBeer) {
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_FESTIVAL_ID), festivalBeer.getFestivalID());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_BEER_ID), festivalBeer.getBeerID());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_NUMBER), festivalBeer.getNumber());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_PAYMENT_ID), festivalBeer.getPaymentID());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_PRICE), festivalBeer.getPrice());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_AWARD), festivalBeer.getAward());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_FIRSTTIME), festivalBeer.isFirstTime());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_STOCK_ID), festivalBeer.getStockID());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_DESCRIPTION), festivalBeer.getDescription());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_REMOTE_ID), festivalBeer.getId());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_IMAGE_ID), festivalBeer.getImageID());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL_BEER.COLUMN_UPDATED_SERVER), festivalBeer.isUpdatedServer());
	}

	public static void bindToInsertHelper(final InsertHelper ih, final Festival festival) {
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL.COLUMN_NAME), festival.getName());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL.COLUMN_DATE), festival.getDate());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL.COLUMN_REMOTE_ID), festival.getId());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL.COLUMN_LOCATION), festival.getLocation());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_FESTIVAL.COLUMN_DESCRIPTION), festival.getDescription());
	}

	public static void bindToInsertHelper(final InsertHelper ih, final Method method) {
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_METHOD.COLUMN_NAME), method.getName());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_METHOD.COLUMN_REMOTE_ID), method.getId());
	}

	public static void bindToInsertHelper(final InsertHelper ih, final Payment payment) {
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_PAYMENT.COLUMN_NAME), payment.getName());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_PAYMENT.COLUMN_REMOTE_ID), payment.getId());
	}

	public static void bindToInsertHelper(final InsertHelper ih, final Stock stock) {
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_STOCK.COLUMN_NAME), stock.getName());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_STOCK.COLUMN_REMOTE_ID), stock.getId());
	}

	public static void bindToInsertHelper(final InsertHelper ih, final UserBeer userBeer) {
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_BEER_ID), userBeer.getBeerId());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_FAVORITE), userBeer.getFavorite());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_RATING), userBeer.getRating());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_USER_ID), userBeer.getUserId());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_WISHLIST), userBeer.getWishlist());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_NOTE), userBeer.getNote());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_USER_NAME), userBeer.getUserName());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_DATE), userBeer.getDate());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_USER_BEER.COLUMN_UPDATED_SERVER), userBeer.isUpdatedServer());
	}

	public static void bindToInsertHelper(final InsertHelper ih, final Beer beer) {
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_NAME), beer.getName());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_BREWER_ID), beer.getBrewerID());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_REMOTE_ID), beer.getId());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_METHOD_ID), beer.getMethodID());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_PERCENTAGE), beer.getPercentage());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_RATING_COUNT), beer.getRatingCount());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_RATING_AVERAGE), beer.getRatingAvg());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_BEER.COLUMN_COLOR), beer.getColor());
	}

	public static void bindToInsertHelper(final InsertHelper ih, final Brewer brewer) {
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_BREWER.COLUMN_NAME), brewer.getName());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_BREWER.COLUMN_DESCRIPTION), brewer.getDescription());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_BREWER.COLUMN_REMOTE_ID), brewer.getId());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_BREWER.COLUMN_LOCATION), brewer.getLocation());
		ih.bind(ih.getColumnIndex(TableHelper.TABLE_BREWER.COLUMN_URL), brewer.getUrl());
	}
}
