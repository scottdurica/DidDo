package com.emroxriprap.diddo.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DidDoContract {

    public static final String CONTENT_AUTHORITY = "com.emroxriprap.diddo";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_LOGS = "logs";

    public static final class DidDoEntry implements BaseColumns{

        public static final String TABLE_NAME = "logs";

        public static final String COLUMN_LOG_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DATE_STRING = "date_string";
        public static final String COLUMN_LOG_TYPE = "log_type";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOGS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOGS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOGS;

        public static Uri buildEntriesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static String getEntryIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }
}