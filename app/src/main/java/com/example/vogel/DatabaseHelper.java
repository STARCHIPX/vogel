package com.example.vogel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.database.sqlite.SQLiteConstraintException;

/**
 * A helper class to manage database creation and version management for a selection database.
 *@noinspection CallToPrintStackTrace
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "selection.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SELECTIONS = "selections";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_OPTION = "option";
    private static final String COLUMN_TIME_OF_DAY = "time_of_day";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_POLYGONS = "polygons";
    private static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String COLUMN_DURATION = "duration";
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";

    /**
     * Constructs a new instance of DatabaseHelper.
     *
     * @param context The context to use for locating paths to the database.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the creation of tables and
     * the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_SELECTIONS  + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_OPTION + " TEXT, " +
                COLUMN_TIME_OF_DAY + " TEXT, " +
                COLUMN_POLYGONS + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIMESTAMP + " TEXT, " +
                COLUMN_DURATION + " TEXT, " +
                COLUMN_USERNAME + " TEXT)";
        db.execSQL(createTable);

        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);

        // Add an admin user
        db.execSQL("INSERT INTO " + TABLE_USERS + " (" + COLUMN_USERNAME + ", " + COLUMN_USER_PASSWORD + ") VALUES ('admin', 'admin')");
    }

    /**
     * Called when the database needs to be upgraded. This method will drop the existing tables
     * and create new ones.
     *
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SELECTIONS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }

    /**
     * Deletes a selection from the database.
     *
     * @param id The ID of the selection to delete.
     * @param username The username of the user attempting to delete the selection.
     * @param isAdmin Whether the user is an admin.
     * @return True if the selection was deleted, false otherwise.
     */
    public boolean deleteSelection(int id, String username, boolean isAdmin) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause;
        String[] whereArgs;

        if (isAdmin) {
            whereClause = COLUMN_ID + "=?";
            whereArgs = new String[]{String.valueOf(id)};
        } else {
            whereClause = COLUMN_ID + "=? AND " + COLUMN_USERNAME + "=?";
            whereArgs = new String[]{String.valueOf(id), username};
        }

        int rowsAffected = db.delete(TABLE_SELECTIONS, whereClause, whereArgs);
        return rowsAffected > 0;
    }

    /**
     * Inserts a new selection into the database.
     *
     * @param option The option selected.
     * @param timeOfDay The time of day selected.
     * @param date The date selected.
     * @param polygons The polygons selected.
     * @param duration The duration selected.
     * @param username The username of the user making the selection.
     * @return True if the insertion was successful, false otherwise.
     */
    public boolean insertSelection(String option, String timeOfDay, String date, String polygons, String duration, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_OPTION, option);
        contentValues.put(COLUMN_TIME_OF_DAY, timeOfDay);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_POLYGONS, polygons);
        contentValues.put(COLUMN_DURATION, duration);
        contentValues.put(COLUMN_USERNAME, username);

        String currentDateAndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        contentValues.put(COLUMN_TIMESTAMP, currentDateAndTime);

        long result = db.insert(TABLE_SELECTIONS, null, contentValues);
        return result != -1;
    }

    /**
        * Inserts a new user into the database.
            *
            * @param username The username of the new user.
     * @param password The password of the new user.
     * @return True if the insertion was successful, false otherwise.
     */
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_USER_PASSWORD, password);

        try {
            long result = db.insertOrThrow(TABLE_USERS, null, contentValues);
            return result != -1;
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all selections from the database.
     *
     * @return A Cursor object containing all selections.
     */
    public Cursor getSelections() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SELECTIONS, null);
    }

    /**
     * Validates a user's login credentials.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return True if the login credentials are valid, false otherwise.
     */
    public boolean validateLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_USER_PASSWORD + "=?", new String[]{username, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }
}