package com.emroxriprap.diddo;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.emroxriprap.diddo.data.DidDoContract;


public class LogsListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {




//    private OnFragmentInteractionListener mListener;
    private static final int DIDDO_LOGS_LOADER = 1;
    private SimpleCursorAdapter mAdapter;
    private CoordinatorLayout mCoordinatorLayout;
    private FloatingActionButton mFab;

    private String [] mDB_TABLE_NAMES = {
            DidDoContract.DidDoEntry.COLUMN_LOG_NAME,
            DidDoContract.DidDoEntry.COLUMN_DATE_STRING,
            DidDoContract.DidDoEntry.COLUMN_DESCRIPTION
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
        getLoaderManager().initLoader(DIDDO_LOGS_LOADER,null,this);
//        // TODO: Change Adapter to display your content
//        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_logs_list, container, false);
        mCoordinatorLayout = (CoordinatorLayout)rootView.findViewById(R.id.coord_layout);
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
                addTestData();
            }
        });

        return rootView;
    }
private void addTestData(){
    ContentValues values = new ContentValues();
    Uri uri = null;
    int count = getListView().getCount();
    for (int i=count; i<count+10; i++){
        values.put(DidDoContract.DidDoEntry.COLUMN_LOG_NAME,"Log " + i);
        values.put(DidDoContract.DidDoEntry.COLUMN_DATE, 1234);
        values.put(DidDoContract.DidDoEntry.COLUMN_DATE_STRING, "09/"+i+"/2015");
        values.put(DidDoContract.DidDoEntry.COLUMN_DESCRIPTION, "Do this "+i+ " times.");
        values.put(DidDoContract.DidDoEntry.COLUMN_LOG_TYPE, 1);
        uri =getActivity().getContentResolver().insert(DidDoContract.DidDoEntry.CONTENT_URI,values);
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
                DidDoContract.DidDoEntry.CONTENT_URI,
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
