package rosko.bojan.rupko.statistics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.v4.content.ContextCompat;

import java.sql.Time;

/**
 * Created by rols on 1/21/17.
 */

public class StatsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Rupko.db";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StatsEntry.TABLE_NAME;

    public StatsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Inner class that defines the table contents */
    public static class StatsEntry implements BaseColumns {
        public static final String TABLE_NAME = "stats";
        public static final String COLUMN_NAME_LEVEL = "level";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_TIME = "time";
    }


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StatsEntry.TABLE_NAME + " (" +
                    StatsEntry._ID + " INTEGER PRIMARY KEY," +
                    StatsEntry.COLUMN_NAME_LEVEL + " TEXT," +
                    StatsEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    StatsEntry.COLUMN_NAME_TIME + " TEXT)";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void insertNewStat(String level, String username, Time time) {
        // Create a new map of values, where column names are the keys

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StatsEntry.COLUMN_NAME_LEVEL, level);
        values.put(StatsEntry.COLUMN_NAME_USERNAME, username);
        values.put(StatsEntry.COLUMN_NAME_TIME, time.getTime());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = sqLiteDatabase.insert(StatsEntry.TABLE_NAME, null, values);
    }

    public Cursor getOrderedStatsForLevel(String level) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StatsEntry._ID,
                StatsEntry.COLUMN_NAME_LEVEL,
                StatsEntry.COLUMN_NAME_USERNAME,
                StatsEntry.COLUMN_NAME_TIME
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = StatsEntry.COLUMN_NAME_LEVEL + " = ?";
        String[] selectionArgs = { level };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                StatsEntry.COLUMN_NAME_TIME + " ASC";

        Cursor cursor = sqLiteDatabase.query(
                StatsEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return cursor;
    }

    public void dropLevelStats(String level) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String command = "DELETE FROM " + StatsEntry.TABLE_NAME +
                " WHERE LEVEL = " + level + ";";
        sqLiteDatabase.execSQL(command);
    }

    public void dropAllStats() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }
}
