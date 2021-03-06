package com.emroxriprap.diddo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by scott on 9/29/2015.
 */
public class DidDoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DidDoLogs.db";

    private static final String COL_ID = "id";
    private static String COL_TASK_NAME = "task_name";
    private static String COL_DATE = "date";

    private static final String CREATE_LOGS_TABLE = "CREATE TABLE " + DidDoContract.Logs.LOGS_TABLE_NAME +
            " (" +
            DidDoContract.Logs._ID + " INTEGER PRIMARY KEY, " +
            DidDoContract.Logs.COLUMN_EVENT_TITLE + " TEXT, " +
            DidDoContract.Logs.COLUMN_WHAT + " TEXT, " +
            DidDoContract.Logs.COLUMN_WITH + " TEXT, " +
            DidDoContract.Logs.COLUMN_DATE_STRING + " TEXT, " +
            DidDoContract.Logs.COLUMN_ALL_DAY_EVENT + " INTEGER, " +
            DidDoContract.Logs.COLUMN_BEGIN_TIME_HOUR + " INTEGER, " +
            DidDoContract.Logs.COLUMN_BEGIN_TIME_MINUTE + " INTEGER, " +
            DidDoContract.Logs.COLUMN_END_TIME_HOUR + " INTEGER, " +
            DidDoContract.Logs.COLUMN_END_TIME_MINUTE + " INTEGER, " +
            DidDoContract.Logs.COLUMN_ADDITIONAL_NOTES + " TEXT "
            +")";

    private static final String CREATE_WHATS_TABLE = "CREATE TABLE " + DidDoContract.Whats.WHATS_TABLE_NAME +
            " (" +
            DidDoContract.Whats._ID + " INTEGER PRIMARY KEY, " +
            DidDoContract.Whats.COLUMN_WHAT_NAME + " TEXT"
            +")";
    private static final String CREATE_WITHS_TABLE = "CREATE TABLE " + DidDoContract.Withs.WITHS_TABLE_NAME +
            " (" +
            DidDoContract.Withs._ID + " INTEGER PRIMARY KEY, " +
            DidDoContract.Withs.COLUMN_WITH_NAME + " TEXT"
            +")";

    public DidDoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_LOGS_TABLE);
        db.execSQL(CREATE_WHATS_TABLE);
        db.execSQL(CREATE_WITHS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DidDoContract.Logs.LOGS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DidDoContract.Whats.WHATS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DidDoContract.Withs.WITHS_TABLE_NAME);
    }
}
