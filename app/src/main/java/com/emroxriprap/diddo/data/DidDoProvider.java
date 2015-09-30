package com.emroxriprap.diddo.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by scott on 9/29/2015.
 */
public class DidDoProvider extends ContentProvider {


    private static final int LOGS = 100;
    private static final int LOG_ID = 102;
    private static final UriMatcher uriMatcher = getUriMatcher();
    private static UriMatcher getUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DidDoContract.CONTENT_AUTHORITY, "logs",LOGS);
        uriMatcher.addURI(DidDoContract.CONTENT_AUTHORITY, "logs/#", LOG_ID);
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
                return DidDoContract.DidDoEntry.CONTENT_TYPE;

            case LOG_ID:
                return DidDoContract.DidDoEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

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
