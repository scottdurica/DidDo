package com.emroxriprap.diddo.inputFragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.emroxriprap.diddo.MainActivity;
import com.emroxriprap.diddo.R;
import com.emroxriprap.diddo.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnFragment extends Fragment {

    public static int yearVal,monthVal,dayOfMonthVal;

    CalendarView calendar;
    FloatingActionButton fab;

    // TODO: Rename and change types and number of parameters
    public static OnFragment newInstance(Bundle args) {
        OnFragment fragment = new OnFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public OnFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_on, container, false);
        calendar = (CalendarView) rootView.findViewById(R.id.cv_date_chooser);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab_next);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                long d = calendar.getDate();

                int dateInt = Utilities.dateToInt(d);
                Log.d("long value ", "" + d);
                Log.d("int value ", "" + dateInt);
                Log.d("converted long value ", "" + Utilities.intToLong(dateInt));
                String dateString = Utilities.dateToString(d);
                getArguments().putString(MainActivity.ARGS_DATE_STRING, Utilities.dateToString(d));
//                getArguments().putInt(MainActivity.ARGS_DATE_INT,Utilities.dateToInt(d));
  //              getArguments().putLong(MainActivity.ARGS_DATE_INT, d);

                AtFragment fragment = AtFragment.newInstance(getArguments());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragment);
                transaction.addToBackStack(null);

                transaction.commit();

            }
        });


        return rootView;
    }


}
