package com.emroxriprap.diddo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by scott on 9/29/2015.
 */
public class DidDoDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DidDoLogs.db";
    private static final String TABLE_NAME = "logs";
    private static final String COL_ID = "id";
    private static String COL_TASK_NAME = "task_name";
    private static String COL_DATE = "date";

    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME +
            " (_id INTEGER PRIMARY KEY, " +
            "task_name TEXT , IMAGEURL TEXT , IMAGEDESC TEXT )";

    private static final String SQL_DROP = "DROP TABLE IS EXISTS " + TABLE_NAME;
    public DidDoDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
