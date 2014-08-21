package nl.endran.showcase.database.sqllite.implementation;

import nl.endran.showcaselib.datacontainers.Beer;
import nl.endran.showcaselib.datacontainers.Brewer;
import nl.endran.showcaselib.datacontainers.Festival;
import nl.endran.showcaselib.datacontainers.FestivalBeer;
import nl.endran.showcaselib.datacontainers.Method;
import nl.endran.showcaselib.datacontainers.Payment;
import nl.endran.showcaselib.datacontainers.Stock;
import nl.endran.showcaselib.datacontainers.UserBeer;

public class UpdateHelper {

	public static String getWhereClause(final Object object) {
		if (object instanceof Beer) {
			return TableHelper.TABLE_BEER.COLUMN_REMOTE_ID + " = ?";
		} else if (object instanceof FestivalBeer) {
			return TableHelper.TABLE_FESTIVAL_BEER.COLUMN_REMOTE_ID + " = ?";
		} else if (object instanceof UserBeer) {
			return TableHelper.TABLE_USER_BEER.COLUMN_BEER_ID + " = ? AND " + TableHelper.TABLE_USER_BEER.COLUMN_USER_ID + " = ?";
		} else if (object instanceof Brewer) {
			return TableHelper.TABLE_BREWER.COLUMN_REMOTE_ID + " = ?";
		} else if (object instanceof Method) {
			return TableHelper.TABLE_METHOD.COLUMN_REMOTE_ID + " = ?";
		} else if (object instanceof Payment) {
			return TableHelper.TABLE_PAYMENT.COLUMN_REMOTE_ID + " = ?";
		} else if (object instanceof Stock) {
			return TableHelper.TABLE_STOCK.COLUMN_REMOTE_ID + " = ?";
		} else if (object instanceof Festival) {
			return TableHelper.TABLE_FESTIVAL.COLUMN_REMOTE_ID + " = ?";
		} else if (object instanceof Application) {
			return TableHelper.TABLE_APPLICATION.COLUMN_REMOTE_ID + " = ?";
		}
		return "";
	}

	public static String[] getWhereArgs(final Object object) {
		String[] whereArgs = null;

		if (object instanceof Beer) {
			whereArgs = new String[] { String.valueOf(((Beer) object).getId()) };
		} else if (object instanceof FestivalBeer) {
			whereArgs = new String[] { String.valueOf(((FestivalBeer) object).getId()) };
		} else if (object instanceof UserBeer) {
			whereArgs = new String[] { String.valueOf(((UserBeer) object).getBeerId()), String.valueOf(((UserBeer) object).getUserId()) };
		} else if (object instanceof Brewer) {
			whereArgs = new String[] { String.valueOf(((Brewer) object).getId()) };
		} else if (object instanceof Method) {
			whereArgs = new String[] { String.valueOf(((Method) object).getId()) };
		} else if (object instanceof Payment) {
			whereArgs = new String[] { String.valueOf(((Payment) object).getId()) };
		} else if (object instanceof Stock) {
			whereArgs = new String[] { String.valueOf(((Stock) object).getId()) };
		} else if (object instanceof Festival) {
			whereArgs = new String[] { String.valueOf(((Festival) object).getId()) };
		}

		return whereArgs;
	}
}
