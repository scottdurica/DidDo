package com.emroxriprap.diddo.inputFragments;


import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.emroxriprap.diddo.LogsListFragment;
import com.emroxriprap.diddo.MainActivity;
import com.emroxriprap.diddo.R;
import com.emroxriprap.diddo.Utilities;
import com.emroxriprap.diddo.data.DidDoContract;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    Button addEvent;
    EditText notes;
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
    public static NotesFragment newInstance(Bundle args) {
        NotesFragment fragment = new NotesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            for (String key: getArguments().keySet()){
                String val = getArguments().get(key).toString();
                Log.d(key, val);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notes, container, false);
        notes = (EditText)rootView.findViewById(R.id.et_notes);
        addEvent = (Button)rootView.findViewById(R.id.b_add_event);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = startCalanderIntent();
//                startActivity(intent);

                // Run query
                Cursor cur = null;
                ContentResolver cr = getActivity().getContentResolver();
                Uri uri = CalendarContract.Calendars.CONTENT_URI;
                String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                        + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                        + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
                String[] selectionArgs = new String[] {"scottdurica@gmail.com", "com.google",
                        "scottdurica@gmail.com"};
// Submit the query and get a Cursor object back.
                cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
                long calID=0;
                while (cur.moveToNext()) {
                    calID = 0;
                    String displayName = null;
                    String accountName = null;
                    String ownerName = null;

                    // Get the field values
                    calID = cur.getLong(PROJECTION_ID_INDEX);
                    displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                    accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                    ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);


                }


                insertIntoCalander(calID);
                saveToLogsDb();
//                Uri uri = getActivity().getContentResolver().insert(CalendarContract.Events.CONTENT_URI,values);

            }

            private void saveToLogsDb() {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DidDoContract.Logs.COLUMN_EVENT_TITLE, getArguments().getString(MainActivity.ARGS_EVENT_TITLE));
                contentValues.put(DidDoContract.Logs.COLUMN_WHAT, getArguments().getString(MainActivity.ARGS_WHAT));
                contentValues.put(DidDoContract.Logs.COLUMN_WITH, getArguments().getString(MainActivity.ARGS_WITH));
                contentValues.put(DidDoContract.Logs.COLUMN_DATE_STRING, getArguments().getString(MainActivity.ARGS_DATE_STRING));
                contentValues.put(DidDoContract.Logs.COLUMN_ALL_DAY_EVENT, getArguments().getInt(MainActivity.ARGS_ALL_DAY_EVENT));
                contentValues.put(DidDoContract.Logs.COLUMN_BEGIN_TIME_HOUR, getArguments().getInt(MainActivity.ARGS_BEGIN_TIME_HOUR));
                contentValues.put(DidDoContract.Logs.COLUMN_BEGIN_TIME_MINUTE, getArguments().getInt(MainActivity.ARGS_BEGIN_TIME_MINUTE));
                contentValues.put(DidDoContract.Logs.COLUMN_END_TIME_HOUR, getArguments().getInt(MainActivity.ARGS_END_TIME_HOUR));
                contentValues.put(DidDoContract.Logs.COLUMN_END_TIME_MINUTE, getArguments().getInt(MainActivity.ARGS_END_TIME_MINUTE));
                contentValues.put(DidDoContract.Logs.COLUMN_ADDITIONAL_NOTES, getArguments().getString(MainActivity.ARGS_DESCRIPTION));

                Uri uri = getActivity().getContentResolver().insert(DidDoContract.Logs.CONTENT_URI,contentValues);
                if (uri != null){
                    LogsListFragment fragment = new LogsListFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }


            private void insertIntoCalander(long calId){
                Calendar beginTime = Calendar.getInstance();
                Calendar endTime = Calendar.getInstance();

                String noteVal = notes.getText().toString();
                int[]timeValues = Utilities.getMonthDayYearFromDateString(getArguments().getString(MainActivity.ARGS_DATE_STRING));
                getArguments().putString(MainActivity.ARGS_DESCRIPTION, noteVal);
                String eventTitle = getArguments().getString(MainActivity.ARGS_WHAT) + "/" + getArguments().getString(MainActivity.ARGS_WITH);
                getArguments().putString(MainActivity.ARGS_EVENT_TITLE,eventTitle);
                ContentValues values = new ContentValues();

                if (getArguments().getInt(MainActivity.ARGS_ALL_DAY_EVENT) == 1){

                    beginTime.set(timeValues[2], timeValues[0] - 1, timeValues[1], 7, 0);
//                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
//                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                    values.put(CalendarContract.Events.ALL_DAY,true);
                    values.put(CalendarContract.Events.DTEND, beginTime.getTimeInMillis());
                }else{
//                    int i = getArguments().getInt(MainActivity.ARGS_BEGIN_TIME);
//                    long sTime = Utilities.intToLong(i);
//                    i = getArguments().getInt(MainActivity.ARGS_END_TIME);
//                    long eTime = Utilities.intToLong(i);
                    beginTime.set(timeValues[2], timeValues[0] - 1, timeValues[1], getArguments().getInt(MainActivity.ARGS_BEGIN_TIME_HOUR), getArguments().getInt(MainActivity.ARGS_BEGIN_TIME_MINUTE));
                    endTime.set(timeValues[2], timeValues[0] - 1, timeValues[1], getArguments().getInt(MainActivity.ARGS_END_TIME_HOUR), getArguments().getInt(MainActivity.ARGS_END_TIME_MINUTE));
//                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
//                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime.getTimeInMillis());
                    values.put(CalendarContract.Events.ALL_DAY,false);
                    values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());

                }
                values.put(CalendarContract.Events.CALENDAR_ID,calId);
                values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
                values.put(CalendarContract.Events.TITLE,"DidDO Event-" + eventTitle );
                values.put(CalendarContract.Events.DESCRIPTION,noteVal);
                values.put(CalendarContract.Events.EVENT_TIMEZONE,"America/New_York");
                values.put(CalendarContract.Events.HAS_ALARM,true);
           Uri uri = getActivity().getContentResolver().insert(CalendarContract.Events.CONTENT_URI,values);
                if (uri != null){
                    Log.d("Values inserted:id/ ", ""+calId);
                    values = new ContentValues();
                    for (int i=0;i<3; i++){

                        values.put(CalendarContract.Reminders.EVENT_ID,Long.parseLong(uri.getLastPathSegment()));
                        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                        if (i==0){
                            values.put(CalendarContract.Reminders.MINUTES,10);
                        }else if (i==1){
                            values.put(CalendarContract.Reminders.MINUTES,120);
                        }else{
                            values.put(CalendarContract.Reminders.MINUTES,720);
                        }
                        Uri uriTwo = getActivity().getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI,values);
                    }
                }

            }
            private Intent startCalanderIntent() {
                Calendar beginTime = Calendar.getInstance();
                Calendar endTime = Calendar.getInstance();

                String noteVal = notes.getText().toString();
                int[]values = Utilities.getMonthDayYearFromDateString(getArguments().getString(MainActivity.ARGS_DATE_STRING));
                getArguments().putString(MainActivity.ARGS_DESCRIPTION, noteVal);
                String eventTitle = getArguments().getString(MainActivity.ARGS_WHAT) + "/" + getArguments().getString(MainActivity.ARGS_WITH);
                getArguments().putString(MainActivity.ARGS_EVENT_TITLE,eventTitle);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.TITLE,"DidDO Event-" + eventTitle)
                        .putExtra(CalendarContract.Events.DESCRIPTION, noteVal);

                if (getArguments().getInt(MainActivity.ARGS_ALL_DAY_EVENT) == 1){

                    beginTime.set(values[2], values[0] - 1, values[1], 7, 0);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,beginTime.getTimeInMillis());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                }else{
//                    int i = getArguments().getInt(MainActivity.ARGS_BEGIN_TIME);
//                    long sTime = Utilities.intToLong(i);
//                    i = getArguments().getInt(MainActivity.ARGS_END_TIME);
//                    long eTime = Utilities.intToLong(i);
                    beginTime.set(values[2], values[0]-1, values[1], getArguments().getInt(MainActivity.ARGS_BEGIN_TIME_HOUR), getArguments().getInt(MainActivity.ARGS_BEGIN_TIME_MINUTE));
                    endTime.set(values[2], values[0] - 1, values[1], getArguments().getInt(MainActivity.ARGS_END_TIME_HOUR), getArguments().getInt(MainActivity.ARGS_END_TIME_MINUTE));
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime.getTimeInMillis());


                }
                return intent;
            }
        });


        return rootView;
    }


}
