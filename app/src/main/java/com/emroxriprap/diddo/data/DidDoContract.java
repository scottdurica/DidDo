package com.emroxriprap.diddo.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DidDoContract {

    public static final String CONTENT_AUTHORITY = "com.emroxriprap.diddo";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_LOGS = "logs";
    public static final String PATH_WHATS = "whats";
    public static final String PATH_WITHS = "withs";

    public static final class Logs implements BaseColumns{

        public static final String LOGS_TABLE_NAME = "logs";

        public static final String COLUMN_EVENT_TITLE = "event_title";
        public static final String COLUMN_WHAT = "what";
        public static final String COLUMN_WITH = "with";
        public static final String COLUMN_DATE_STRING = "date_string";
        public static final String COLUMN_ALL_DAY_EVENT = "all_day_event";
        public static final String COLUMN_BEGIN_TIME_HOUR = "begin_time_hour";
        public static final String COLUMN_BEGIN_TIME_MINUTE = "begin_time_minute";
        public static final String COLUMN_END_TIME_HOUR = "end_time_hour";
        public static final String COLUMN_END_TIME_MINUTE = "end_time_minute";
        public static final String COLUMN_ADDITIONAL_NOTES = "description";
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

    public static final class Whats implements BaseColumns {

        public static final String WHATS_TABLE_NAME = "whats";

        public static final String COLUMN_WHAT_NAME = "name";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WHATS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WHATS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WHATS;

        public static Uri buildWhatsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static String getWhatIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class Withs implements BaseColumns {

        public static final String WITHS_TABLE_NAME = "withs";

        public static final String COLUMN_WITH_NAME = "name";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WITHS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WITHS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WITHS;

        public static Uri buildWithsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static String getWithIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

}
