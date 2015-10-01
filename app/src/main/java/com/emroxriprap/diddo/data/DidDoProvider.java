package com.emroxriprap.diddo.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by scott on 9/29/2015.
 */
public class DidDoProvider extends ContentProvider {

    private DidDoDbHelper mOpenHelper;
    private static final int LOGS = 100;
    private static final int LOG_ID = 102;
    private static final UriMatcher sUriMatcher = getsUriMatcher();
    private static UriMatcher getsUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DidDoContract.CONTENT_AUTHORITY, DidDoContract.PATH_LOGS,LOGS);
        uriMatcher.addURI(DidDoContract.CONTENT_AUTHORITY, DidDoContract.PATH_LOGS + "/#", LOG_ID);
        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new DidDoDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)){
            case LOGS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DidDoContract.DidDoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case LOG_ID: {
               retCursor = getLog(uri, projection, sortOrder, LOG_ID);
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }
private Cursor getLog(Uri uri, String[]projection, String sortOrder, int requester){
    switch (requester){
        case LOG_ID: {
            String id = DidDoContract.DidDoEntry.getEntryIdFromUri(uri);
            return mOpenHelper.getReadableDatabase().query(
                    DidDoContract.DidDoEntry.TABLE_NAME,
                    projection,
                    "WHERE id = ?",
                    new String[]{id},
                    null,
                    null,
                    sortOrder
            );
        }
        default:
            throw new UnsupportedOperationException("Couldn't retreive data from " + uri);
    }
}
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
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
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri = null;
        switch (match){
            case LOGS: {
                long _id = db.insert(DidDoContract.DidDoEntry.TABLE_NAME,null,values);
                if (_id > 0){
                    returnUri = DidDoContract.DidDoEntry.buildEntriesUri(_id);
                    Toast.makeText(getContext(), "Inserted into Entries DB", Toast.LENGTH_SHORT).show();

                }
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
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
