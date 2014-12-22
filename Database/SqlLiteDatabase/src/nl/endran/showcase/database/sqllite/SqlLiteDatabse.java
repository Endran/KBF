package nl.endran.showcase.database.sqllite;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import nl.endran.showcase.database.sqllite.implementation.CursorConverter;
import nl.endran.showcase.database.sqllite.implementation.DataBaseHandler;
import nl.endran.showcase.database.sqllite.queries.GetAllSummariesForSearchInputQuery;
import nl.endran.showcase.database.sqllite.queries.GetAllSummariesQuery;
import nl.endran.showcase.database.sqllite.queries.GetAllUserBeersForBeerQuery;
import nl.endran.showcase.database.sqllite.queries.GetAllUserBeersQuery;
import nl.endran.showcase.database.sqllite.queries.GetBrewerQuery;
import nl.endran.showcase.database.sqllite.queries.GetFestivalQuery;
import nl.endran.showcase.database.sqllite.queries.GetNotServerUpdatedUserBeersQuery;
import nl.endran.showcase.database.sqllite.queries.GetSummariesByBeerrIdListQuery;
import nl.endran.showcase.database.sqllite.queries.GetUserBeerQuery;
import nl.endran.showcase.database.sqllite.queries.helpers.QueryHelper;
import nl.endran.showcaselib.connectors.ShowcaseDatabase;
import nl.endran.showcaselib.datacontainers.Beer;
import nl.endran.showcaselib.datacontainers.Brewer;
import nl.endran.showcaselib.datacontainers.CustomServerCredentials;
import nl.endran.showcaselib.datacontainers.Festival;
import nl.endran.showcaselib.datacontainers.FestivalBeer;
import nl.endran.showcaselib.datacontainers.ShowcaseDataObject;
import nl.endran.showcaselib.datacontainers.Summary;
import nl.endran.showcaselib.datacontainers.UserBeer;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;

public class SqlLiteDatabse implements ShowcaseDatabase {
    ILogger log = LoggerFactory.GetLogger(this);

    private final Context context;
    private boolean initialized;

    public SqlLiteDatabse(final Context context) {
        this.context = context;
        new DataBaseHandler(context);
    }

    @Override
    public String getLatestUpdateTimestamp() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getString("TIMESTAMP", "");
    }

    private void setLatestUpdateTimestamp(final String timestamp) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sharedPreferences.edit().putString("TIMESTAMP", timestamp).commit();

        log.debug("## " + "setLatestUpdateTimestamp " + timestamp);
    }

    private boolean isInitialized() {
        if (!initialized) {
            final String timestamp = getLatestUpdateTimestamp();
            if (timestamp != null && timestamp.length() > 0) {
                initialized = true;
            }
        }
        return initialized;
    }

    @Override
    public boolean storeShowcaseDataObjectListList(final List<ArrayList<ShowcaseDataObject>> showcaseDataObjectListList) {
        boolean succes = true;

        if (!isInitialized()) {
            final DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
            for (final ArrayList<ShowcaseDataObject> showcaseDataObjectList : showcaseDataObjectListList) {
                succes = dataBaseHandler.bulkInsert(showcaseDataObjectList);
                checkForTimestamp(showcaseDataObjectList);
            }
        } else {
            for (final ArrayList<ShowcaseDataObject> showcaseDataObjectList : showcaseDataObjectListList) {
                succes &= storeShowcaseDataObjectList(showcaseDataObjectList);
            }
        }

        log.verbose("storeShowcaseDataObjectListList succeeded, latestUpdateTimestamp = " + getLatestUpdateTimestamp());
        return succes;
    }

    @Override
    public boolean storeShowcaseDataObjectList(final List<ShowcaseDataObject> showcaseDataObjectList) {
        final DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
        final int insertdOrUpdatedItems = dataBaseHandler.insertOrUpdate(showcaseDataObjectList);
        final boolean succes = insertdOrUpdatedItems > 0;

        if (succes) {
            checkForTimestamp(showcaseDataObjectList);
        }

        log.verbose("storeShowcaseDataObjectList succeeded, latestUpdateTimestamp = " + getLatestUpdateTimestamp());
        return succes;
    }

    @Override
    public boolean storeShowcaseDataObject(final ShowcaseDataObject showcaseDataObject) {
        final DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
        final boolean succes = dataBaseHandler.insertOrUpdate(showcaseDataObject) > 0;

        if (succes) {
            checkForTimestamp(showcaseDataObject);
        }

        log.verbose("storeShowcaseDataObject succeeded, latestUpdateTimestamp = " + getLatestUpdateTimestamp());
        return false;
    }

    private void checkForTimestamp(final List<ShowcaseDataObject> showcaseDataObjectList) {
        for (final ShowcaseDataObject showcaseDataObject : showcaseDataObjectList) {
            checkForTimestamp(showcaseDataObject);
        }
    }

    private void checkForTimestamp(final ShowcaseDataObject showcaseDataObject) {
        try {
            final java.lang.reflect.Method getDateMethod = showcaseDataObject.getClass().getMethod("getDate", new Class<?>[0]);
            final String timestamp = (String) getDateMethod.invoke(showcaseDataObject, new Object[0]);
            if (timestamp == null || timestamp.length() != "2012-12-15 18:34:56".length()) {
                return;
            }

            final String latestUpdateTimestamp = getLatestUpdateTimestamp();

            log.debug("## " + latestUpdateTimestamp + ".compareTo(" + timestamp + ")=" + latestUpdateTimestamp.compareTo(timestamp));

            if (!isInitialized() || latestUpdateTimestamp.compareTo(timestamp) < 0) {
                setLatestUpdateTimestamp(timestamp);
            }

        } catch (final NoSuchMethodException e) {
            // Expected
        } catch (final IllegalArgumentException e) {
            log.error("IllegalArgumentException", e);
        } catch (final IllegalAccessException e) {
            log.error("IllegalAccessException", e);
        } catch (final InvocationTargetException e) {
            log.error("InvocationTargetException", e);
        }
    }

    @Override
    public List<Summary> getAllSummaries() {
        final Cursor resultCursor = QueryHelper.doQuery(new DataBaseHandler(context), new GetAllSummariesQuery());

        final List<Summary> summaries = new ArrayList<Summary>();

        resultCursor.moveToFirst();

        while (!resultCursor.isAfterLast()) {
            final Summary summary = CursorConverter.toSummary(resultCursor);
            summaries.add(summary);
            resultCursor.moveToNext();
        }
        resultCursor.close();

        return summaries;
    }

    @Override
    public Summary getSummary(final int festivalBeerId) {
        final Cursor resultCursor = QueryHelper.doQuery(new DataBaseHandler(context), new GetAllSummariesQuery(festivalBeerId));

        Summary summary = null;

        resultCursor.moveToFirst();

        if (!resultCursor.isAfterLast()) {
            summary = CursorConverter.toSummary(resultCursor);
        }
        resultCursor.close();

        return summary;
    }

    @Override
    public List<Beer> getAllBeers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Beer getBeer(final int beerId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Brewer> getAllBrewers() {
        final Cursor resultCursor = QueryHelper.doQuery(new DataBaseHandler(context), new GetBrewerQuery());

        final List<Brewer> brewers = new ArrayList<Brewer>();

        resultCursor.moveToFirst();

        while (!resultCursor.isAfterLast()) {
            final Brewer brewer = CursorConverter.toBrewer(resultCursor);
            brewers.add(brewer);
            resultCursor.moveToNext();
        }
        resultCursor.close();

        return brewers;
    }

    @Override
    public Brewer getBrewer(final int brewerId) {
        final Cursor resultCursor = QueryHelper.doQuery(new DataBaseHandler(context), new GetBrewerQuery(brewerId));

        Brewer brewer = null;

        resultCursor.moveToFirst();

        if (!resultCursor.isAfterLast()) {
            brewer = CursorConverter.toBrewer(resultCursor);
        }
        resultCursor.close();

        return brewer;
    }

    @Override
    public List<Festival> getAllFestivals() {
        final Cursor resultCursor = QueryHelper.doQuery(new DataBaseHandler(context), new GetFestivalQuery());

        final List<Festival> festivals = new ArrayList<Festival>();

        resultCursor.moveToFirst();

        while (!resultCursor.isAfterLast()) {
            final Festival festival = CursorConverter.toFestival(resultCursor);
            festivals.add(festival);
            resultCursor.moveToNext();
        }
        resultCursor.close();

        return festivals;
    }

    @Override
    public Festival getFestival(final int festivalId) {
        final Cursor resultCursor = QueryHelper.doQuery(new DataBaseHandler(context), new GetFestivalQuery(festivalId));
        Festival festival = null;

        resultCursor.moveToFirst();

        if (!resultCursor.isAfterLast()) {
            festival = CursorConverter.toFestival(resultCursor);
        }
        resultCursor.close();

        return festival;
    }

    @Override
    public List<FestivalBeer> getAllFestivalBeers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FestivalBeer getFestivalBeer(final int festivalBeerId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserBeer getUserBeer(final int beerId) {

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        final int userId = sharedPreferences.getInt("USER_ID", 0);
        return getUserBeer(beerId, userId);
    }

    @Override
    public UserBeer getUserBeer(final int beerId, final int userId) {
        final Cursor resultCursor = QueryHelper.doQuery(new DataBaseHandler(context), new GetUserBeerQuery(beerId, userId));

        UserBeer userBeer = null;

        resultCursor.moveToFirst();

        if (!resultCursor.isAfterLast()) {
            userBeer = CursorConverter.toUserBeer(resultCursor);
        }
        resultCursor.close();

        return userBeer;
    }

    @Override
    public void setCustomServerCredentials(final CustomServerCredentials customServerCredentials) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        sharedPreferences.edit().putString("SERVER_KEY", customServerCredentials.getServerKey()).putInt("USER_ID", customServerCredentials.getUserId())
                .putBoolean("ACTIVATED", customServerCredentials.isActivated()).putString("EMAIL", customServerCredentials.getEmail())
                .putString("USER_NAME", customServerCredentials.getUserName()).commit();

    }

    @Override
    public void resetCustomServerCredentials() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        sharedPreferences.edit().putString("SERVER_KEY", "").putInt("USER_ID", -2).putBoolean("ACTIVATED", false).putString("EMAIL", "")
                .putString("USER_NAME", "").commit();

    }

    @Override
    public CustomServerCredentials getCustomServerCredentials() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        final CustomServerCredentials customServerCredentials = new CustomServerCredentials();
        customServerCredentials.setServerKey(sharedPreferences.getString("SERVER_KEY", ""));
        customServerCredentials.setUserId(sharedPreferences.getInt("USER_ID", -2));
        customServerCredentials.setActivated(sharedPreferences.getBoolean("ACTIVATED", false));
        customServerCredentials.setEmail(sharedPreferences.getString("EMAIL", ""));
        customServerCredentials.setUserName(sharedPreferences.getString("USER_NAME", ""));

        return customServerCredentials;
    }

    @Override
    public boolean storeUserRating(final int beerId, final int rating, final String note, final boolean wishlist, final boolean favorite) {

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        final int userId = sharedPreferences.getInt("USER_ID", -1);
        if (userId == 0) {
            return false;
        }

        UserBeer userBeer = getUserBeer(beerId, userId);
        if (userBeer == null) {
            userBeer = new UserBeer();
            userBeer.setUserId(userId);
        }

        userBeer.setBeerId(beerId);
        userBeer.setRating(rating);
        userBeer.setNote(note);
        userBeer.setWishlist(wishlist);
        userBeer.setFavorite(favorite);
        userBeer.setUpdatedServer(false);

        final DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
        final int updated = dataBaseHandler.insertOrUpdate(userBeer);

        return updated > 0;
        //
        //
        // succes &= storeShowcaseDataObjectList(showcaseDataObjectList);
    }

    @Override
    public List<UserBeer> getAllUserBeerRatingsForCurrentUser() {

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        final int userId = sharedPreferences.getInt("USER_ID", -1);

        if (userId == 0) {
            return new ArrayList<UserBeer>();
        }

        final Cursor resultCursor = QueryHelper.doQuery(new DataBaseHandler(context), new GetAllUserBeersQuery(userId));

        final List<UserBeer> userBeers = new ArrayList<UserBeer>();

        resultCursor.moveToFirst();

        while (!resultCursor.isAfterLast()) {
            final UserBeer userBeer = CursorConverter.toUserBeer(resultCursor);
            userBeers.add(userBeer);
            resultCursor.moveToNext();
        }
        resultCursor.close();

        return userBeers;
    }

    @Override
    public List<UserBeer> getAllUserBeersForBeer(final int beerId) {

        final Cursor resultCursor = QueryHelper.doQuery(new DataBaseHandler(context), new GetAllUserBeersForBeerQuery(beerId));

        final List<UserBeer> userBeers = new ArrayList<UserBeer>();

        resultCursor.moveToFirst();

        while (!resultCursor.isAfterLast()) {
            final UserBeer userBeer = CursorConverter.toUserBeer(resultCursor);
            userBeers.add(userBeer);
            resultCursor.moveToNext();
        }
        resultCursor.close();

        return userBeers;
    }

    @Override
    public List<Summary> getSummaryByBeerIdList(final List<Integer> beerIdList) {
        if (beerIdList == null || beerIdList.size() <= 0) {
            return new ArrayList<Summary>();
        }

        final Cursor resultCursor = QueryHelper.doQuery(new DataBaseHandler(context), new GetSummariesByBeerrIdListQuery(beerIdList));

        final List<Summary> summaries = new ArrayList<Summary>();

        resultCursor.moveToFirst();

        while (!resultCursor.isAfterLast()) {
            final Summary summary = CursorConverter.toSummary(resultCursor);
            summaries.add(summary);
            resultCursor.moveToNext();
        }
        resultCursor.close();

        return summaries;
    }

    @Override
    public List<UserBeer> getNotServerUpdatedUserBeers() {

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        final int userId = sharedPreferences.getInt("USER_ID", -1);

        if (userId == 0) {
            return new ArrayList<UserBeer>();
        }

        final Cursor resultCursor = QueryHelper.doQuery(new DataBaseHandler(context), new GetNotServerUpdatedUserBeersQuery(userId));

        final List<UserBeer> userBeers = new ArrayList<UserBeer>();

        resultCursor.moveToFirst();

        while (!resultCursor.isAfterLast()) {
            final UserBeer userBeer = CursorConverter.toUserBeer(resultCursor);
            userBeers.add(userBeer);
            resultCursor.moveToNext();
        }
        resultCursor.close();

        return userBeers;
    }

    @Override
    public boolean setServerUpdatedUserBeers(final List<UserBeer> userBeerList) {

        for (final UserBeer userBeer : userBeerList) {
            userBeer.setUpdatedServer(true);
        }

        final DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
        final int updated = dataBaseHandler.insertOrUpdate(userBeerList);

        return updated > 0;
    }

    @Override
    public List<Summary> getAllSummariesForSearchInput(final String searchInput) {
        final Cursor resultCursor = QueryHelper.doQuery(new DataBaseHandler(context), new GetAllSummariesForSearchInputQuery(searchInput));

        final List<Summary> summaries = new ArrayList<Summary>();

        resultCursor.moveToFirst();

        while (!resultCursor.isAfterLast()) {
            final Summary summary = CursorConverter.toSummary(resultCursor);
            summaries.add(summary);
            resultCursor.moveToNext();
        }
        resultCursor.close();

        return summaries;
    }
}
