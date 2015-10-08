package com.emroxriprap.diddo.inputFragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emroxriprap.diddo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AtFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AtFragment extends Fragment {

    public static AtFragment newInstance(Bundle args) {
        AtFragment fragment = new AtFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AtFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_at, container, false);
    }


}
