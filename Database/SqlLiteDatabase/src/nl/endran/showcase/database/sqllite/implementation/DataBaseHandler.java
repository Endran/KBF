package nl.endran.showcase.database.sqllite.implementation;

import java.util.ArrayList;
import java.util.List;

import nl.endran.activity.EndranTracking;
import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

public class DataBaseHandler extends SQLiteOpenHelper {
    protected final ILogger log = LoggerFactory.GetLogger(this);
    protected final Context context;

    private static final String DATABASE_NAME = "ShowcaseSqlLite";
    public static final int DATABASE_VERSION = 15;

    public static final String DATABASE_CONTENT_KEY = "DATABASE_CONTENT_ID";

    public static final String WHERE = " WHERE ";
    public static final String ORDERBY = " ORDER BY ";
    public static final String DESC = " DESC ";
    public static final String ASC = " ASC ";
    public static final String FROM = " FROM ";
    public static final String SELECT = " SELECT ";

    public DataBaseHandler(final Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        if (!sharedPreferences.getBoolean("DATABASE_HACK_YOU_WILL_REGRET", false)) {
            sharedPreferences.edit().clear().commit();
            sharedPreferences.edit().putBoolean(EndranTracking.ALLOW_STAT_KEY, true).commit();
            sharedPreferences.edit().putBoolean("DATABASE_HACK_YOU_WILL_REGRET", true).commit();
        }
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        log.verbose("Database will be created using the following strings:");
        log.verbose(TableHelper.TABLE_BEER.getCreateString());
        log.verbose(TableHelper.TABLE_BREWER.getCreateString());
        log.verbose(TableHelper.TABLE_FESTIVAL.getCreateString());
        log.verbose(TableHelper.TABLE_FESTIVAL_BEER.getCreateString());
        log.verbose(TableHelper.TABLE_METHOD.getCreateString());
        log.verbose(TableHelper.TABLE_PAYMENT.getCreateString());
        log.verbose(TableHelper.TABLE_STOCK.getCreateString());
        log.verbose(TableHelper.TABLE_IMAGE.getCreateString());
        log.verbose(TableHelper.TABLE_USER_BEER.getCreateString());
        log.verbose(TableHelper.TABLE_APPLICATION.getCreateString());

        create(db);
        log.verbose("Database created succesfully");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        drop(db);
        onCreate(db);
    }

    private void create(final SQLiteDatabase db) {
        db.execSQL(TableHelper.TABLE_BEER.getCreateString());
        db.execSQL(TableHelper.TABLE_BREWER.getCreateString());
        db.execSQL(TableHelper.TABLE_FESTIVAL.getCreateString());
        db.execSQL(TableHelper.TABLE_FESTIVAL_BEER.getCreateString());
        db.execSQL(TableHelper.TABLE_METHOD.getCreateString());
        db.execSQL(TableHelper.TABLE_PAYMENT.getCreateString());
        db.execSQL(TableHelper.TABLE_STOCK.getCreateString());
        db.execSQL(TableHelper.TABLE_IMAGE.getCreateString());
        db.execSQL(TableHelper.TABLE_USER_BEER.getCreateString());
        db.execSQL(TableHelper.TABLE_APPLICATION.getCreateString());
    }

    private void drop(final SQLiteDatabase db) {
        log.info("Droppig database");
        db.execSQL(TableHelper.KEYWORD_DROP_TABLE + TableHelper.TABLE_BEER.NAME);
        db.execSQL(TableHelper.KEYWORD_DROP_TABLE + TableHelper.TABLE_BREWER.NAME);
        db.execSQL(TableHelper.KEYWORD_DROP_TABLE + TableHelper.TABLE_FESTIVAL.NAME);
        db.execSQL(TableHelper.KEYWORD_DROP_TABLE + TableHelper.TABLE_FESTIVAL_BEER.NAME);
        db.execSQL(TableHelper.KEYWORD_DROP_TABLE + TableHelper.TABLE_METHOD.NAME);
        db.execSQL(TableHelper.KEYWORD_DROP_TABLE + TableHelper.TABLE_PAYMENT.NAME);
        db.execSQL(TableHelper.KEYWORD_DROP_TABLE + TableHelper.TABLE_STOCK.NAME);
        db.execSQL(TableHelper.KEYWORD_DROP_TABLE + TableHelper.TABLE_IMAGE.NAME);
        db.execSQL(TableHelper.KEYWORD_DROP_TABLE + TableHelper.TABLE_USER_BEER.NAME);
        db.execSQL(TableHelper.KEYWORD_DROP_TABLE + TableHelper.TABLE_APPLICATION.NAME);
    }

    public void dropAndCreate() {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            db.beginTransaction();
            drop(db);
            create(db);
            db.setTransactionSuccessful();
        } catch (final SQLiteException ex) {
            log.error("insert SQLiteException occurred", ex);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    public Cursor query(final String table, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy,
            final String having, final String orderBy, final String limit) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        } catch (final SQLiteException ex) {
            log.error("SQLiteException occurred", ex);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return cursor;
    }

    public Cursor rawQuery(final String sql, final String[] selectionArgs) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(sql, selectionArgs);
        } catch (final SQLiteException ex) {
            log.error("SQLiteException occurred", ex);
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return cursor;
    }

    // --------------------- INSERT

    public int insert(final Object object) {
        final List<Object> objectList = new ArrayList<Object>();
        objectList.add(object);
        return insert(objectList);
    }

    public synchronized <T> int insert(final List<T> objectList) {
        log.verbose("insert");
        SQLiteDatabase db = null;
        int rowCount = 0;

        if (objectList == null || objectList.size() <= 0) {
            return 0;
        }

        final String tableName = TableHelper.getTableName(objectList.get(0));
        log.verbose("update tableName=" + tableName);

        try {
            db = this.getWritableDatabase();
            db.beginTransaction();
            for (final T object : objectList) {
                final ContentValues values = ContentValuesHelper.getContentValues(object);
                final long rowId = db.insert(TableHelper.getTableName(object), null, values);
                if (rowId >= 0) {
                    rowCount++;
                }
            }
            db.setTransactionSuccessful();
        } catch (final SQLiteException ex) {
            log.error("insert SQLiteException occurred", ex);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }

        log.verbose("insert rowCount=" + rowCount);
        return rowCount;
    }

    // --------------------- UPDATE

    public List<Object> update(final Object object) {
        final ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add(object);
        return update(objectList, null, null);
    }

    public <T> List<T> update(final List<T> objectList) {
        if (objectList == null || objectList.size() <= 0) {
            return new ArrayList<T>();
        }

        return update(objectList, null, null);
    }

    public List<Object> update(final Object object, final String whereClause, final String[] whereArgs) {
        final ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add(object);
        return update(objectList, whereClause, whereArgs);
    }

    public synchronized <T> List<T> update(final List<T> objectList, String whereClause, String[] whereArgs) {
        log.verbose("update");

        if (objectList == null || objectList.size() <= 0) {
            return new ArrayList<T>();
        }

        final String tableName = TableHelper.getTableName(objectList.get(0));
        log.verbose("update tableName=" + tableName);

        SQLiteDatabase db = null;
        int totalColumnsUpdated = 0;

        final ArrayList<T> failedList = new ArrayList<T>();

        try {
            db = this.getWritableDatabase();
            db.beginTransaction();
            if (whereClause == null) {
                whereClause = UpdateHelper.getWhereClause(objectList.get(0));
                for (final T object : objectList) {
                    whereArgs = UpdateHelper.getWhereArgs(object);
                    final ContentValues values = ContentValuesHelper.getContentValues(object);
                    final int columnsUpdated = db.update(TableHelper.getTableName(object), values, whereClause, whereArgs);
                    totalColumnsUpdated += columnsUpdated;
                    if (columnsUpdated == 0) {
                        failedList.add(object);
                    }
                }
            } else {
                for (final T object : objectList) {
                    final ContentValues values = ContentValuesHelper.getContentValues(object);
                    totalColumnsUpdated += db.update(TableHelper.getTableName(object), values, whereClause, whereArgs);
                }
            }
            db.setTransactionSuccessful();
        } catch (final SQLiteException ex) {
            log.error("update SQLiteException occurred", ex);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }

        log.verbose("update columnsUpdated=" + totalColumnsUpdated);
        return failedList;
    }

    // --------------------- INSERT OR UPDATE

    public int insertOrUpdate(final Object object) {
        final ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add(object);
        return insertOrUpdate(objectList, null, null);
    }

    public <T> int insertOrUpdate(final List<T> objectList) {
        if (objectList == null || objectList.size() <= 0) {
            return -1;
        }
        return insertOrUpdate(objectList, null, null);
    }

    public int insertOrUpdate(final Object object, final String whereClause, final String[] whereArgs) {
        final ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add(object);
        return insertOrUpdate(objectList, whereClause, whereArgs);
    }

    public <T> int insertOrUpdate(final List<T> objectList, final String whereClause, final String[] whereArgs) {
        log.verbose("insertOrUpdate");

        if (objectList == null || objectList.size() <= 0) {
            return -1;
        }

        final String tableName = TableHelper.getTableName(objectList.get(0));
        log.verbose("insertOrUpdate tableName=" + tableName);

        int columnsUpdated = objectList.size();
        final List<T> failedUpdateList = update(objectList, whereClause, whereArgs);
        if (failedUpdateList.size() > 0) {
            columnsUpdated -= failedUpdateList.size();
            columnsUpdated += insert(failedUpdateList);
        }

        if (columnsUpdated != objectList.size()) {
            log.warn("Did not update/insert as many columns (" + columnsUpdated + ") as that there are items in the list (" + objectList.size() + ")");
        }

        log.verbose("insertOrUpdate columnsUpdated=" + columnsUpdated);
        return columnsUpdated;
    }

    // --------------------- BULK INSERT

    public synchronized <T> boolean bulkInsert(final List<T> objectList) {
        log.verbose("bulkInsert");
        if (objectList == null || objectList.size() <= 0) {
            return false;
        }

        SQLiteDatabase db = null;
        InsertHelper ih = null;

        final String tableName = TableHelper.getTableName(objectList.get(0));
        log.verbose("bulkInsert tableName=" + tableName);

        boolean succes = false;

        try {
            db = this.getWritableDatabase();
            ih = new InsertHelper(db, tableName);

            // db.execSQL("PRAGMA synchronous=OFF");
            db.setLockingEnabled(false);
            db.beginTransaction();
            for (final Object object : objectList) {
                ih.prepareForInsert();
                InsertHelperBindHelper.bindToInsertHelper(ih, object);
                final long rowId = ih.execute();
                if (rowId <= 0) {
                    throw new SQLiteException("bulkInsert InsertHelper execute returned " + rowId);
                }
            }
            db.setTransactionSuccessful();
            succes = true;
        } catch (final SQLiteException ex) {
            log.error("bulkInsert SQLiteException occurred", ex);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.setLockingEnabled(true);
                // db.execSQL("PRAGMA synchronous=NORMAL");
                db.close();
            }
            if (ih != null) {
                ih.close();
            }
        }

        log.verbose("bulkInsert succes=" + succes);
        return succes;
    }
}
