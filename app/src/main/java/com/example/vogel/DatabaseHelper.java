package com.example.vogel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "selection.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "selections";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_OPTION = "option";
    private static final String COLUMN_TIME_OF_DAY = "time_of_day";
    private static final String COLUMN_TIMESTAMP = "timestamp";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_OPTION + " TEXT, " +
                    COLUMN_TIME_OF_DAY + " TEXT, " +
                    COLUMN_TIMESTAMP + " TEXT)";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public boolean insertSelection(String option, String timeOfDay) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_OPTION, option);
            contentValues.put(COLUMN_TIME_OF_DAY, timeOfDay);

            String currentDateAndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            contentValues.put(COLUMN_TIMESTAMP, currentDateAndTime);

            long result = db.insert(TABLE_NAME, null, contentValues);
            return result != -1;
        }

        public Cursor getSelections() {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        }
}