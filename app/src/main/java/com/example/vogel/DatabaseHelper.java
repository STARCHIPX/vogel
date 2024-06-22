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

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

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

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SELECTIONS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }

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

    public Cursor getSelections() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SELECTIONS, null);
    }

    public boolean validateLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_USER_PASSWORD + "=?", new String[]{username, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }
}