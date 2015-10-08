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
    private static final int LOG_ID = 101;
    private static final int WHATS = 110;
    private static final int WHAT_ID = 111;
    private static final int WITHS = 120;
    private static final int WITH_ID = 121;

    private static final UriMatcher sUriMatcher = getsUriMatcher();
    private static UriMatcher getsUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DidDoContract.CONTENT_AUTHORITY, DidDoContract.PATH_LOGS,LOGS);
        uriMatcher.addURI(DidDoContract.CONTENT_AUTHORITY, DidDoContract.PATH_LOGS + "/#", LOG_ID);
        uriMatcher.addURI(DidDoContract.CONTENT_AUTHORITY, DidDoContract.PATH_WHATS,WHATS);
        uriMatcher.addURI(DidDoContract.CONTENT_AUTHORITY, DidDoContract.PATH_WHATS + "/#", WHAT_ID);
        uriMatcher.addURI(DidDoContract.CONTENT_AUTHORITY, DidDoContract.PATH_WITHS,WITHS);
        uriMatcher.addURI(DidDoContract.CONTENT_AUTHORITY, DidDoContract.PATH_WITHS + "/#", WITH_ID);
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
                        DidDoContract.Logs.LOGS_TABLE_NAME,
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
               retCursor = getSingleItem(uri, projection, sortOrder, LOG_ID);
            }
            case WHATS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DidDoContract.Whats.WHATS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case WHAT_ID: {
                retCursor = getSingleItem(uri, projection, sortOrder, WHAT_ID);
            }
            case WITHS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DidDoContract.Withs.WITHS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case WITH_ID: {
                retCursor = getSingleItem(uri,projection,sortOrder,WITH_ID);
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }
private Cursor getSingleItem(Uri uri, String[] projection, String sortOrder, int requester){
    switch (requester){
        case LOG_ID: {
            String id = DidDoContract.Logs.getEntryIdFromUri(uri);
            return mOpenHelper.getReadableDatabase().query(
                    DidDoContract.Logs.LOGS_TABLE_NAME,
                    projection,
                    "WHERE id = ?",
                    new String[]{id},
                    null,
                    null,
                    sortOrder
            );
        }
        case WHAT_ID: {
            String id = DidDoContract.Whats.getWhatIdFromUri(uri);
            return mOpenHelper.getReadableDatabase().query(
                    DidDoContract.Whats.WHATS_TABLE_NAME,
                    projection,
                    "WHERE id = ?",
                    new String[]{id},
                    null,
                    null,
                    sortOrder
            );
        }
        case WITH_ID: {
            String id = DidDoContract.Withs.getWithIdFromUri(uri);
            return mOpenHelper.getReadableDatabase().query(
                    DidDoContract.Withs.WITHS_TABLE_NAME,
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
                return DidDoContract.Logs.CONTENT_TYPE;
            case LOG_ID:
                return DidDoContract.Logs.CONTENT_ITEM_TYPE;
            case WHATS:
                return DidDoContract.Logs.CONTENT_TYPE;
            case WHAT_ID:
                return DidDoContract.Logs.CONTENT_ITEM_TYPE;
            case WITHS:
                return DidDoContract.Logs.CONTENT_TYPE;
            case WITH_ID:
                return DidDoContract.Logs.CONTENT_ITEM_TYPE;
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
                long _id = db.insert(DidDoContract.Logs.LOGS_TABLE_NAME,null,values);
                if (_id > 0){
                    returnUri = DidDoContract.Logs.buildEntriesUri(_id);
                    Toast.makeText(getContext(), "Inserted into Entries DB", Toast.LENGTH_SHORT).show();

                }
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case WHATS: {
                long _id = db.insert(DidDoContract.Whats.WHATS_TABLE_NAME,null,values);
                if (_id > 0){
                    returnUri = DidDoContract.Whats.buildWhatsUri(_id);
                    Toast.makeText(getContext(), "Inserted into Whats DB", Toast.LENGTH_SHORT).show();

                }
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case WITHS: {
                long _id = db.insert(DidDoContract.Withs.WITHS_TABLE_NAME,null,values);
                if (_id > 0){
                    returnUri = DidDoContract.Withs.buildWithsUri(_id);
                    Toast.makeText(getContext(), "Inserted into Withs DB", Toast.LENGTH_SHORT).show();

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
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int success=0;

        switch (match){
            case WHATS: {
                success = db.delete(DidDoContract.Whats.WHATS_TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            case WITHS: {
                success = db.delete(DidDoContract.Withs.WITHS_TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (success>0){
            Toast.makeText(getContext(),"Deleted",Toast.LENGTH_SHORT).show();
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
