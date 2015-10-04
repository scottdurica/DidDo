package com.emroxriprap.diddo.inputFragments;


import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

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
    private FloatingActionButton mFab;
    private EditText dialogNewEntry;
    private Button dialogAdd, dialogCancel;
    private Button.OnClickListener DialogAddOnClickListener;
    private Dialog addDialog;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_do_what, container, false);
        mCoordinatorLayout = (CoordinatorLayout)rootView.findViewById(R.id.what_list_coord_layout);

        mList = (ListView)rootView.findViewById(R.id.lv_whats);
        int [] listItemVIewIds = {R.id.tv_what_list_name};

        mAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_item_whats,
                null,
                mDB_TABLE_NAMES,
                listItemVIewIds,
                0
        );
        mList.setAdapter(mAdapter);

        mFab = (FloatingActionButton)rootView.findViewById(R.id.fab_add_what);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go  to new fragment
                //             addTestData();
                //              insertIntoCal();
//                DoWhatFragment fragment = new DoWhatFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
                addDialog = showDialog();
                addDialog.show();

            }
        });
        DialogAddOnClickListener = new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //check for duplicates then add to db
                        if (dialogNewEntry.getText().toString().trim().length()>0){
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
                                if (val.trim().equalsIgnoreCase(dialogNewEntry.getText().toString())){
                                    Toast.makeText(getActivity(),"Value already exists",Toast.LENGTH_SHORT).show();
                                    addDialog.dismiss();
                                    exists = true;
                                }
                            }
                            if (exists == false){
                                ContentValues values = new ContentValues();
                                values.put(DidDoContract.Whats.COLUMN_WHAT_NAME,dialogNewEntry.getText().toString());

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
                        Log.d("getting ", "here");
                       addDialog.dismiss();
                    }
                };
        return rootView;
    }

private Dialog showDialog(){
    Dialog dialog = new Dialog(getActivity());
    dialog.setContentView(R.layout.dialog_add_what);
    dialog.setTitle("Add a What");



    dialogNewEntry = (EditText)dialog.findViewById(R.id.et_dialog_add_what_name);
    dialogAdd = (Button)dialog.findViewById(R.id.b_dialog_add_what);
    dialogCancel = (Button)dialog.findViewById(R.id.b_dialog_cancel_what);

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
