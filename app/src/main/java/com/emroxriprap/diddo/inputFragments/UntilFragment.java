package com.emroxriprap.diddo.inputFragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TimePicker;

import com.emroxriprap.diddo.MainActivity;
import com.emroxriprap.diddo.R;
import com.emroxriprap.diddo.Utilities;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UntilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UntilFragment extends Fragment {

    TimePicker timePicker;
    FloatingActionButton fab;
//    public static int selEndHour, selEndMin;

    public static UntilFragment newInstance(Bundle args) {
        UntilFragment fragment = new UntilFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public UntilFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_until, container, false);
        timePicker = (TimePicker)rootView.findViewById(R.id.tp_time);
        timePicker.setCurrentHour(getArguments().getInt(MainActivity.ARGS_BEGIN_TIME_HOUR));
        timePicker.setCurrentMinute(getArguments().getInt(MainActivity.ARGS_BEGIN_TIME_MINUTE));


        fab = (FloatingActionButton)rootView.findViewById(R.id.fab_next);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                int []values = Utilities.getMonthDayYearFromEpoch(getArguments().getLong(MainActivity.ARGS_DATE_INT));
//                Calendar c = Calendar.getInstance();
//                c.set(values[2], values[0], values[1], timePicker.getCurrentHour(), timePicker.getCurrentMinute());
//                long endTime = c.getTimeInMillis();
//                int endTimeInt = Utilities.dateToInt(endTime);
//                getArguments().putLong(MainActivity.ARGS_END_TIME, endTime);
                getArguments().putInt(MainActivity.ARGS_END_TIME_HOUR,timePicker.getCurrentHour());
                getArguments().putInt(MainActivity.ARGS_END_TIME_MINUTE,timePicker.getCurrentMinute());

                NotesFragment fragment = NotesFragment.newInstance(getArguments());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        return  rootView;

    }


}
