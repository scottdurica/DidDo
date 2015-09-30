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

    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + DidDoContract.DidDoEntry.TABLE_NAME +
            " (" +
            DidDoContract.DidDoEntry._ID + " INTEGER PRIMARY KEY," +
            DidDoContract.DidDoEntry.COLUMN_LOG_NAME + " TEXT," +
            DidDoContract.DidDoEntry.COLUMN_DESCRIPTION + " TEXT" +
            DidDoContract.DidDoEntry.COLUMN_DATE + " INTEGER," +
            DidDoContract.DidDoEntry.COLUMN_DATE_STRING + " TEXT" +
            DidDoContract.DidDoEntry.COLUMN_LOG_TYPE + " INTEGER"
            +")";


    public DidDoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DidDoContract.DidDoEntry.TABLE_NAME);
    }
}
