package com.emroxriprap.diddo.inputFragments;


import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.emroxriprap.diddo.MainActivity;
import com.emroxriprap.diddo.R;
import com.emroxriprap.diddo.data.DidDoContract;

import java.util.ArrayList;
import java.util.List;


public class DoWhatFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final int WHATS_LOADER = 2;
    private SimpleCursorAdapter mAdapter;
    private ListView mList;
    private CoordinatorLayout mCoordinatorLayout;
//    private FloatingActionButton mFab;
    private EditText dialogNewEntry;
    private Button dialogAdd, dialogCancel;
    private Button.OnClickListener DialogAddOnClickListener;
    private Dialog addDialog;
    private String mSelectedListVal;
    private Button.OnClickListener DialogCancelOnClickListener;
    private final String[] mDB_TABLE_NAMES = {
            DidDoContract.Whats.COLUMN_WHAT_NAME
    };

    // TODO: Rename and change types and number of parameters
    public static DoWhatFragment newInstance(Bundle args) {
        DoWhatFragment fragment = new DoWhatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DoWhatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        getLoaderManager().initLoader(WHATS_LOADER,null,this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_do_what, container, false);
        mCoordinatorLayout = (CoordinatorLayout)rootView.findViewById(R.id.what_list_coord_layout);

        mList = (ListView)rootView.findViewById(R.id.lv_whats);
        int [] listItemVIewIds = {R.id.tv_single_item_name};

        mAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_item_single_tv,
                null,
                mDB_TABLE_NAMES,
                listItemVIewIds,
                0
        );
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String selectedVal =((TextView) view.findViewById(R.id.tv_single_item_name)).getText().toString();
//                Log.d("selectedVal ", selectedVal);
                Bundle args = new Bundle();
                args.putString(MainActivity.ARGS_WHAT,selectedVal);
                WithWhoFragment fragment = WithWhoFragment.newInstance(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = getActivity().getContentResolver().query(
                        DidDoContract.Whats.CONTENT_URI,
                        new String[]{DidDoContract.Whats._ID,DidDoContract.Whats.COLUMN_WHAT_NAME},
                        null,
                        null,
                        null,
                        null
                );
                while(cursor.moveToNext()){
                    String dbVal = cursor.getString(cursor.getColumnIndex(DidDoContract.Whats.COLUMN_WHAT_NAME));
                    mSelectedListVal = ((TextView)view.findViewById(R.id.tv_single_item_name)).getText().toString();
                    if (dbVal.trim().equalsIgnoreCase(mSelectedListVal)){
                        //delete row
                        String id = cursor.getString(cursor.getColumnIndex(DidDoContract.Whats._ID));
//                        int i = cursor.getInt(cursor.getColumnIndex(DidDoContract.Whats._ID));
//                        String id = cursor.getString(cursor.getColumnIndex(DidDoContract.Whats._ID));

                        getActivity().getContentResolver().delete(
                                DidDoContract.Whats.CONTENT_URI,
                                "_ID= ?",
                                new String[]{id}
                        );
                        Snackbar.make(mCoordinatorLayout, dbVal+" Deleted", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ContentValues values = new ContentValues();
                                values.put(DidDoContract.Whats.COLUMN_WHAT_NAME, mSelectedListVal);
                                getActivity().getContentResolver().insert(
                                        DidDoContract.Whats.CONTENT_URI,
                                        values
                                );
                            }
                        }).show();

                    }
                }

                return true;
            }
        });
//        mFab = (FloatingActionButton)rootView.findViewById(R.id.fab_add_what);
//        mFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //go  to new fragment
//                //             addTestData();
//                //              insertIntoCal();
////                addDialog = showDialog();
////                addDialog.show();
//
//            }
//        });
        DialogAddOnClickListener = new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                //check for duplicates then add to db
                        String etValTrimmed = dialogNewEntry.getText().toString().trim();
                if (etValTrimmed.length()>0){
                    boolean exists = false;
                    Cursor cursor = getActivity().getContentResolver().query(
                            DidDoContract.Whats.CONTENT_URI,
                            null,
                            null,
                            null,
                            null
                    );
                    while(cursor.moveToNext()){
                        String val = cursor.getString(cursor.getColumnIndex(DidDoContract.Whats.COLUMN_WHAT_NAME));
                        if (val.trim().equalsIgnoreCase(etValTrimmed)){
                            Toast.makeText(getActivity(),"Value already exists",Toast.LENGTH_SHORT).show();
                            addDialog.dismiss();
                            exists = true;
                        }
                    }
                    if (exists == false){
                        ContentValues values = new ContentValues();
                        values.put(DidDoContract.Whats.COLUMN_WHAT_NAME,etValTrimmed);

                        Uri uri = getActivity().getContentResolver().insert(
                             DidDoContract.Whats.CONTENT_URI,values);
                        addDialog.dismiss();
                    }

                }else{
                    Toast.makeText(getActivity(),"Value can't be empty",Toast.LENGTH_SHORT).show();
                }

            }
        };
        DialogCancelOnClickListener = new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                       addDialog.dismiss();
                    }
                };
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.do_what_or_with_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:{

                return true;
            }
            case R.id.action_add: {
                addDialog = showDialog();
                addDialog.show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Dialog showDialog(){
    Dialog dialog = new Dialog(getActivity());
    dialog.setContentView(R.layout.dialog_add_single_value);
    dialog.setTitle("Add a What");
    dialogNewEntry = (EditText)dialog.findViewById(R.id.et_dialog_add_name);
    dialogAdd = (Button)dialog.findViewById(R.id.b_dialog_add);
    dialogCancel = (Button)dialog.findViewById(R.id.b_dialog_cancel);
    dialogAdd.setOnClickListener(DialogAddOnClickListener);
    dialogCancel.setOnClickListener(DialogCancelOnClickListener);
    return dialog;
}
private void addTestData(){
    List<String>verbs = new ArrayList<>();
    verbs.add("Confirm");
    verbs.add("Email");
    verbs.add("Invoice");
    verbs.add("Discuss");
    Uri uri = null;
    for (String s: verbs){
        ContentValues values = new ContentValues();
        values.put(DidDoContract.Whats.COLUMN_WHAT_NAME,s);
        uri = getActivity().getContentResolver().insert(
                DidDoContract.Whats.CONTENT_URI,values
        );
    }
}
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(
                getActivity(),
                DidDoContract.Whats.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ((SimpleCursorAdapter)mList.getAdapter()).swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((SimpleCursorAdapter)mList.getAdapter()).swapCursor(null);
    }
}
