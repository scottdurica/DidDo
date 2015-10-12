package com.emroxriprap.diddo;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.emroxriprap.diddo.data.DidDoContract;
import com.emroxriprap.diddo.inputFragments.DoWhatFragment;

import java.util.Calendar;


public class LogsListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {




//    private OnFragmentInteractionListener mListener;
    private static final int LOGS_LOADER = 1;
    private SimpleCursorAdapter mAdapter;
    private CoordinatorLayout mCoordinatorLayout;
    private FloatingActionButton mFab;

    private final String [] mDB_TABLE_NAMES = {
            DidDoContract.Logs.COLUMN_EVENT_TITLE,
            DidDoContract.Logs.COLUMN_DATE_STRING,
            DidDoContract.Logs.COLUMN_ADDITIONAL_NOTES
    };

    public static LogsListFragment newInstance(Bundle bundle) {
        LogsListFragment fragment = new LogsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LogsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
        getLoaderManager().initLoader(LOGS_LOADER,null,this);
//        // TODO: Change Adapter to display your content
//        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_logs_list, container, false);
        mCoordinatorLayout = (CoordinatorLayout)rootView.findViewById(R.id.log_list_coord_layout);
        int [] listItemViewIds = {R.id.tv_log_list_item_name, R.id.tv_log_list_item_date_string,
                                  R.id.tv_log_list_item_description};

        mAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_item_logs,
                null,
                mDB_TABLE_NAMES,
                listItemViewIds,
                0 );
        setListAdapter(mAdapter);
        mFab = (FloatingActionButton)rootView.findViewById(R.id.fab_add_record);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go  to new fragment
  //              addTestData();
  //              insertIntoCal();
                DoWhatFragment fragment= new DoWhatFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return rootView;
    }
    private void insertIntoCal() {
        final String[] EVENT_PROJECTION =
                new String[]{
                        CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                        CalendarContract.Calendars.OWNER_ACCOUNT
                };

// The indices for the projection array above.
        final int PROJECTION_ID_INDEX = 0;
        final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
        final int PROJECTION_DISPLAY_NAME_INDEX = 2;
        final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

        // Run query
        Cursor cur = null;
        ContentResolver cr = getActivity().getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[]{"scottdurica@gmail.com", "com.google",
                "scottdurica@gmail.com"};
// Submit the query and get a Cursor object back.
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

        // Use the cursor to step through the returned records
        while (cur.moveToNext()) {
            long calID = 0;
            String displayName = null;
            String accountName = null;
            String ownerName = null;

            // Get the field values
            calID = cur.getLong(PROJECTION_ID_INDEX);
            displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

            Log.d("callID ", "" + calID);          // Do something with the values...
            Log.d("display name ", "" + displayName);          // Do something with the values...
            Log.d("account name ", "" + accountName);          // Do something with the values...
            Log.d("owner name ", "" + ownerName);          // Do something with the values...


        }



        long calID = 2;
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015, 9, 14, 7, 30);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2015, 9, 14, 8, 45);
        endMillis = endTime.getTimeInMillis();


//        ContentResolver cr = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "Jazzercise");
        values.put(CalendarContract.Events.DESCRIPTION, "Group workout");
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        Uri uri2 = cr.insert(CalendarContract.Events.CONTENT_URI, values);

// get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri2.getLastPathSegment());

    }

private void addTestData(){
    ContentValues values = new ContentValues();
    Uri uri = null;
    int count = getListView().getCount();
    for (int i=count; i<count+10; i++){
        values.put(DidDoContract.Logs.COLUMN_EVENT_TITLE,"Log " + i);
//        values.put(DidDoContract.Logs.COLUMN_DATE, 1234);
        values.put(DidDoContract.Logs.COLUMN_DATE_STRING, "09/"+i+"/2015");
        values.put(DidDoContract.Logs.COLUMN_ADDITIONAL_NOTES, "Do this "+i+ " times.");
//        values.put(DidDoContract.Logs.COLUMN_LOG_TYPE, 1);
        uri =getActivity().getContentResolver().insert(DidDoContract.Logs.CONTENT_URI,values);
    }
//    if (uri != null){
//        Toast.makeText(getActivity(), "Saved to DB", Toast.LENGTH_SHORT).show();
//
//    }
}
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

//        if (null != mListener) {
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
//        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader loader = new CursorLoader(
                getActivity(),
                DidDoContract.Logs.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        ((SimpleCursorAdapter)this.getListAdapter()).swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((SimpleCursorAdapter)this.getListAdapter()).swapCursor(null);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(String id);
//    }

}
