package nl.endran.showcase.database.sqllite.implementation;

import android.text.TextUtils;

import nl.endran.showcaselib.datacontainers.Beer;
import nl.endran.showcaselib.datacontainers.Brewer;
import nl.endran.showcaselib.datacontainers.Festival;
import nl.endran.showcaselib.datacontainers.FestivalBeer;
import nl.endran.showcaselib.datacontainers.Method;
import nl.endran.showcaselib.datacontainers.Payment;
import nl.endran.showcaselib.datacontainers.Stock;
import nl.endran.showcaselib.datacontainers.UserBeer;

public class TableHelper {

	private static final String KEYWORD_CREATE_TABLE = "CREATE TABLE ";
	public static final String KEYWORD_DROP_TABLE = "DROP TABLE IF EXISTS ";
	private static final String KEYWORD_START_COLUMNS = "(";
	private static final String KEYWORD_END_COLUMNS = ")";
	private static final String KEYWORD_END_OF_COLUMN = ",";

	private static final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY";
	private static final String INTEGER = " INTEGER";
	private static final String TEXT = " TEXT";
	private static final String REAL = " REAL";
	private static final String BLOB = " BLOB";

	private static String startTable(final String name) {
		return TextUtils.concat(KEYWORD_CREATE_TABLE, name, KEYWORD_START_COLUMNS).toString();
	}

	private static String addColumn(final String name, final String type) {
		return TextUtils.concat(name, type, KEYWORD_END_OF_COLUMN).toString();
	}

	private static String addColumn(final String name, final String type, final boolean lastColumn) {
		return TextUtils.concat(name, type, lastColumn ? KEYWORD_END_COLUMNS : KEYWORD_END_OF_COLUMN).toString();
	}

	public static final class TABLE_APPLICATION {
		public final static String NAME = TABLE_APPLICATION.class.getSimpleName();

		public final static String COLUMN_ID = "_id";
		public final static String COLUMN_REMOTE_ID = "APPLICATION_remoteId";
		public final static String COLUMN_TIMESTAMP = "APPLICATION_timestamp";

		public final static String TYPE_ID = INTEGER_PRIMARY_KEY;
		public final static String TYPE_REMOTE_ID = INTEGER;
		public final static String TYPE_TIMESTAMP = TEXT;

		public static String getCreateString() {
			return TextUtils.concat(startTable(NAME), addColumn(COLUMN_ID, TYPE_ID), addColumn(COLUMN_REMOTE_ID, TYPE_REMOTE_ID),
					addColumn(COLUMN_TIMESTAMP, TYPE_TIMESTAMP, true)).toString();
		}
	}

	public static final class TABLE_IMAGE {
		public final static String NAME = TABLE_IMAGE.class.getSimpleName();

		public final static String COLUMN_ID = "_id";
		public final static String COLUMN_REMOTE_ID = "IMAGE_remoteId";
		public final static String COLUMN_IMAGE = "IMAGE_image";

		public final static String TYPE_ID = INTEGER_PRIMARY_KEY;
		public final static String TYPE_REMOTE_ID = INTEGER;
		public final static String TYPE_IMAGE = BLOB;

		public static String getCreateString() {
			return TextUtils.concat(startTable(NAME), addColumn(COLUMN_ID, TYPE_ID), addColumn(COLUMN_REMOTE_ID, TYPE_REMOTE_ID),
					addColumn(COLUMN_IMAGE, TYPE_IMAGE, true)).toString();
		}
	}

	public static final class TABLE_BEER {
		public final static String NAME = TABLE_BEER.class.getSimpleName();

		public final static String COLUMN_ID = "_id";
		public final static String COLUMN_REMOTE_ID = "BEER_remoteId";
		public final static String COLUMN_NAME = "BEER_name";
		public final static String COLUMN_METHOD_ID = "BEER_methodId";
		public final static String COLUMN_PERCENTAGE = "BEER_percentage";
		public final static String COLUMN_RATING_AVERAGE = "BEER_ratingAvg";
		public final static String COLUMN_COLOR = "BEER_color";
		public final static String COLUMN_RATING_COUNT = "BEER_ratingCount";
		public final static String COLUMN_BREWER_ID = "BEER_brewerId";

		public final static String TYPE_ID = INTEGER_PRIMARY_KEY;
		public final static String TYPE_REMOTE_ID = INTEGER;
		public final static String TYPE_NAME = TEXT;
		public final static String TYPE_COLOR = TEXT;
		public final static String TYPE_METHOD_ID = INTEGER;
		public final static String TYPE_PERCENTAGE = REAL;
		public final static String TYPE_RATING_AVERAGE = REAL;
		public final static String TYPE_RATING_COUNT = INTEGER;
		public final static String TYPE_BREWER_ID = INTEGER;

		public static String getCreateString() {
			return TextUtils.concat(startTable(NAME), addColumn(COLUMN_ID, TYPE_ID), addColumn(COLUMN_REMOTE_ID, TYPE_REMOTE_ID),
					addColumn(COLUMN_NAME, TYPE_NAME), addColumn(COLUMN_COLOR, TYPE_COLOR), addColumn(COLUMN_BREWER_ID, TYPE_BREWER_ID),
					addColumn(COLUMN_METHOD_ID, TYPE_METHOD_ID), addColumn(COLUMN_PERCENTAGE, TYPE_PERCENTAGE),
					addColumn(COLUMN_RATING_AVERAGE, TYPE_RATING_AVERAGE), addColumn(COLUMN_RATING_COUNT, TYPE_RATING_COUNT, true)).toString();
		}
	}

	public static final class TABLE_BREWER {
		public final static String NAME = TABLE_BREWER.class.getSimpleName();

		public final static String COLUMN_ID = "_id";
		public final static String COLUMN_REMOTE_ID = "BREWER_remoteId";
		public final static String COLUMN_NAME = "BREWER_name";
		public final static String COLUMN_LOCATION = "BREWER_location";
		public final static String COLUMN_URL = "BREWER_url";
		public final static String COLUMN_DESCRIPTION = "BREWER_description";

		public final static String TYPE_ID = INTEGER_PRIMARY_KEY;
		public final static String TYPE_REMOTE_ID = INTEGER;
		public final static String TYPE_NAME = TEXT;
		public final static String TYPE_LOCATION = TEXT;
		public final static String TYPE_URL = TEXT;
		public final static String TYPE_DESCRIPTION = TEXT;

		public static String getCreateString() {
			return TextUtils.concat(startTable(NAME), addColumn(COLUMN_ID, TYPE_ID), addColumn(COLUMN_REMOTE_ID, TYPE_REMOTE_ID),
					addColumn(COLUMN_NAME, TYPE_NAME), addColumn(COLUMN_LOCATION, TYPE_LOCATION), addColumn(COLUMN_URL, TYPE_URL),
					addColumn(COLUMN_DESCRIPTION, TYPE_DESCRIPTION, true)).toString();
		}
	}

	public static final class TABLE_PAYMENT {
		public final static String NAME = TABLE_PAYMENT.class.getSimpleName();

		public final static String COLUMN_ID = "_id";
		public final static String COLUMN_REMOTE_ID = "PAYMENT_remoteId";
		public final static String COLUMN_NAME = "PAYMENT_name";

		public final static String TYPE_ID = INTEGER_PRIMARY_KEY;
		public final static String TYPE_REMOTE_ID = INTEGER;
		public final static String TYPE_NAME = TEXT;

		public static String getCreateString() {
			return TextUtils.concat(startTable(NAME), addColumn(COLUMN_ID, TYPE_ID), addColumn(COLUMN_REMOTE_ID, TYPE_REMOTE_ID),
					addColumn(COLUMN_NAME, TYPE_NAME, true)).toString();
		}
	}

	public static final class TABLE_STOCK {
		public final static String NAME = TABLE_STOCK.class.getSimpleName();

		public final static String COLUMN_ID = "_id";
		public final static String COLUMN_REMOTE_ID = "STOCK_remoteId";
		public final static String COLUMN_NAME = "STOCK_name";

		public final static String TYPE_ID = INTEGER_PRIMARY_KEY;
		public final static String TYPE_REMOTE_ID = INTEGER;
		public final static String TYPE_NAME = TEXT;

		public static String getCreateString() {
			return TextUtils.concat(startTable(NAME), addColumn(COLUMN_ID, TYPE_ID), addColumn(COLUMN_REMOTE_ID, TYPE_REMOTE_ID),
					addColumn(COLUMN_NAME, TYPE_NAME, true)).toString();
		}
	}

	public static final class TABLE_METHOD {
		public final static String NAME = TABLE_METHOD.class.getSimpleName();

		public final static String COLUMN_ID = "_id";
		public final static String COLUMN_REMOTE_ID = "METHOD_remoteId";
		public final static String COLUMN_NAME = "METHOD_name";

		public final static String TYPE_ID = INTEGER_PRIMARY_KEY;
		public final static String TYPE_REMOTE_ID = INTEGER;
		public final static String TYPE_NAME = TEXT;

		public static String getCreateString() {
			return TextUtils.concat(startTable(NAME), addColumn(COLUMN_ID, TYPE_ID), addColumn(COLUMN_REMOTE_ID, TYPE_REMOTE_ID),
					addColumn(COLUMN_NAME, TYPE_NAME, true)).toString();
		}
	}

	public static final class TABLE_FESTIVAL {
		public final static String NAME = TABLE_FESTIVAL.class.getSimpleName();

		public final static String COLUMN_ID = "_id";
		public final static String COLUMN_REMOTE_ID = "FESTIVAL_remoteId";
		public final static String COLUMN_NAME = "FESTIVAL_name";
		public final static String COLUMN_DATE = "FESTIVAL_date";
		public final static String COLUMN_LOCATION = "FESTIVAL_location";
		public final static String COLUMN_DESCRIPTION = "FESTIVAL_description";

		public final static String TYPE_ID = INTEGER_PRIMARY_KEY;
		public final static String TYPE_REMOTE_ID = INTEGER;
		public final static String TYPE_NAME = TEXT;
		public final static String TYPE_DATE = INTEGER;
		public final static String TYPE_LOCATION = TEXT;
		public final static String TYPE_DESCRIPTION = TEXT;

		public static String getCreateString() {
			return TextUtils.concat(startTable(NAME), addColumn(COLUMN_ID, TYPE_ID), addColumn(COLUMN_REMOTE_ID, TYPE_REMOTE_ID),
					addColumn(COLUMN_NAME, TYPE_NAME), addColumn(COLUMN_DATE, TYPE_DATE), addColumn(COLUMN_LOCATION, TYPE_LOCATION),
					addColumn(COLUMN_DESCRIPTION, TYPE_DESCRIPTION, true)).toString();
		}
	}

	public static final class TABLE_FESTIVAL_BEER {
		public final static String NAME = TABLE_FESTIVAL_BEER.class.getSimpleName();

		public final static String COLUMN_ID = "_id";
		public final static String COLUMN_REMOTE_ID = "FESTIVAL_BEER_remoteId";
		public final static String COLUMN_IMAGE_ID = "FESTIVAL_BEER_imageId";
		public final static String COLUMN_FESTIVAL_ID = "FESTIVAL_BEER_festivalId";
		public final static String COLUMN_BEER_ID = "FESTIVAL_BEER_beerId";
		public final static String COLUMN_PAYMENT_ID = "FESTIVAL_BEER_paymentId";
		public final static String COLUMN_PRICE = "FESTIVAL_BEER_price";
		public final static String COLUMN_AWARD = "FESTIVAL_BEER_award";
		public final static String COLUMN_FIRSTTIME = "FESTIVAL_BEER_firstTime";
		public final static String COLUMN_STOCK_ID = "FESTIVAL_BEER_stockID";
		public final static String COLUMN_DESCRIPTION = "FESTIVAL_BEER_description";
		public final static String COLUMN_NUMBER = "FESTIVAL_BEER_number";
		public final static String COLUMN_UPDATED_SERVER = "FESTIVAL_BEER_updated_server";

		public final static String TYPE_ID = INTEGER_PRIMARY_KEY;
		public final static String TYPE_REMOTE_ID = INTEGER;
		public final static String TYPE_IMAGE_ID = INTEGER;
		public final static String TYPE_FESTIVAL_ID = INTEGER;
		public final static String TYPE_BEER_ID = INTEGER;
		public final static String TYPE_PAYMENT_ID = INTEGER;
		public final static String TYPE_PRICE = REAL;
		public final static String TYPE_AWARD = TEXT;
		public final static String TYPE_FIRSTTIME = INTEGER;
		public final static String TYPE_STOCK_ID = INTEGER;
		public final static String TYPE_DESCRIPTION = TEXT;
		public final static String TYPE_NUMBER = INTEGER;
		public final static String TYPE_UPDATED_SERVER = INTEGER;

		public static String getCreateString() {
			return TextUtils.concat(startTable(NAME), addColumn(COLUMN_ID, TYPE_ID), addColumn(COLUMN_REMOTE_ID, TYPE_REMOTE_ID),
					addColumn(COLUMN_IMAGE_ID, TYPE_IMAGE_ID), addColumn(COLUMN_FESTIVAL_ID, TYPE_FESTIVAL_ID), addColumn(COLUMN_BEER_ID, TYPE_BEER_ID),
					addColumn(COLUMN_PAYMENT_ID, TYPE_PAYMENT_ID), addColumn(COLUMN_PRICE, TYPE_PRICE), addColumn(COLUMN_AWARD, TYPE_AWARD),
					addColumn(COLUMN_FIRSTTIME, TYPE_FIRSTTIME), addColumn(COLUMN_STOCK_ID, TYPE_STOCK_ID), addColumn(COLUMN_NUMBER, TYPE_NUMBER),
					addColumn(COLUMN_UPDATED_SERVER, TYPE_UPDATED_SERVER), addColumn(COLUMN_DESCRIPTION, TYPE_DESCRIPTION, true)).toString();
		}
	}

	public static final class TABLE_USER_BEER {
		public final static String NAME = TABLE_USER_BEER.class.getSimpleName();

		public final static String COLUMN_ID = "_id";
		public final static String COLUMN_USER_ID = "USER_BEER_userId";
		public final static String COLUMN_USER_NAME = "USER_BEER_userName";
		public final static String COLUMN_BEER_ID = "USER_BEER_beerId";
		public final static String COLUMN_RATING = "USER_BEER_rating";
		public final static String COLUMN_FAVORITE = "USER_BEER_favorite";
		public final static String COLUMN_WISHLIST = "USER_BEER_wishlist";
		public final static String COLUMN_NOTE = "USER_BEER_note";
		public final static String COLUMN_UPDATED_SERVER = "USER_BEER_updated_server";
		public final static String COLUMN_DATE = "USER_BEER_date";

		public final static String TYPE_ID = INTEGER_PRIMARY_KEY;
		public final static String TYPE_USER_ID = INTEGER;
		public final static String TYPE_USER_NAME_ID = TEXT;
		public final static String TYPE_BEER_ID = INTEGER;
		public final static String TYPE_RATING = INTEGER;
		public final static String TYPE_FAVORITE = INTEGER;
		public final static String TYPE_WISHLIST = INTEGER;
		public final static String TYPE_NOTE = TEXT;
		public final static String TYPE_UPDATED_SERVER = INTEGER;
		public final static String TYPE_DATE = TEXT;

		public static String getCreateString() {
			return TextUtils.concat(startTable(NAME), addColumn(COLUMN_ID, TYPE_ID), addColumn(COLUMN_USER_ID, TYPE_USER_ID),
					addColumn(COLUMN_BEER_ID, TYPE_BEER_ID), addColumn(COLUMN_USER_NAME, TYPE_USER_NAME_ID), addColumn(COLUMN_RATING, TYPE_RATING),
					addColumn(COLUMN_FAVORITE, TYPE_FAVORITE), addColumn(COLUMN_UPDATED_SERVER, TYPE_UPDATED_SERVER), addColumn(COLUMN_DATE, TYPE_DATE),
					addColumn(COLUMN_NOTE, TYPE_NOTE), addColumn(COLUMN_WISHLIST, TYPE_WISHLIST, true)).toString();
		}
	}

	public static String getTableName(final Object object) {
		if (object instanceof Beer) {
			return TABLE_BEER.NAME;
		} else if (object instanceof FestivalBeer) {
			return TABLE_FESTIVAL_BEER.NAME;
		} else if (object instanceof UserBeer) {
			return TABLE_USER_BEER.NAME;
		} else if (object instanceof Brewer) {
			return TABLE_BREWER.NAME;
		} else if (object instanceof Method) {
			return TABLE_METHOD.NAME;
		} else if (object instanceof Payment) {
			return TABLE_PAYMENT.NAME;
		} else if (object instanceof Stock) {
			return TABLE_STOCK.NAME;
		} else if (object instanceof Festival) {
			return TABLE_FESTIVAL.NAME;
		} else if (object instanceof Application) {
			return TABLE_APPLICATION.NAME;
		}
		return "";
	}
}
