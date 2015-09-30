package com.emroxriprap.diddo.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by scott on 9/29/2015.
 */
public class DidDoProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "com.emroxriprap.diddo.data";
    private static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/data");
    private static final int LOGS = 1;
    private static final int LOG_ID = 2;
    private static final UriMatcher uriMatcher = getUriMatcher();
    private static UriMatcher getUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "logs",LOGS);
        uriMatcher.addURI(PROVIDER_NAME, "logs/#", LOG_ID);
        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case LOGS:
                return "vnd.android.cursor.dir/vnd.com.emroxriprap.diddo.data.logs";

            case LOG_ID:
                return "vnd.android.cursor.item/vnd.com.emroxriprap.diddo.data.logs";

        }
        return "";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
