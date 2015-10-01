package com.emroxriprap.diddo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class DidDoFragment extends Fragment {

    private Button doIt,didIt;


    public DidDoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.frag_did_do, container, false);
        doIt=(Button)rootView.findViewById(R.id.b_do_it);
        didIt = (Button)rootView.findViewById(R.id.b_did_it);
        doIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("do it", " clicked");
            }
        });
        didIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("did it", " was pressed");
            }
        });
        return rootView;
    }
}
