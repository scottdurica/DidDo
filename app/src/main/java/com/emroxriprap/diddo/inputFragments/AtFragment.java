package com.emroxriprap.diddo.inputFragments;


import android.animation.FloatArrayEvaluator;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
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
 * Use the {@link AtFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AtFragment extends Fragment {

    private TimePicker timePicker;
    private CheckBox allDayCb;
    FloatingActionButton fab;
//    public static int selStartHour,selStartMin;

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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_at, container, false);
        timePicker = (TimePicker)rootView.findViewById(R.id.tp_time);
        timePicker.setCurrentHour(8);
        timePicker.setCurrentMinute(0);
        timePicker.setVisibility(View.INVISIBLE);

        allDayCb = (CheckBox)rootView.findViewById(R.id.cb_all_day_event);
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab_next);

        allDayCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    timePicker.setVisibility(View.INVISIBLE);
//                    timePicker.setEnabled(false);
                }else{
                    timePicker.setVisibility(View.VISIBLE);
//                    timePicker.setEnabled(true);
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allDayCb.isChecked()) {
                    getArguments().putInt(MainActivity.ARGS_ALL_DAY_EVENT, 1);
                    getArguments().putInt(MainActivity.ARGS_BEGIN_TIME_HOUR,0);
                    getArguments().putInt(MainActivity.ARGS_BEGIN_TIME_MINUTE,0);
                    getArguments().putInt(MainActivity.ARGS_END_TIME_HOUR,0);
                    getArguments().putInt(MainActivity.ARGS_END_TIME_MINUTE,0);
                    NotesFragment fragment = NotesFragment.newInstance(getArguments());
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    getArguments().putInt(MainActivity.ARGS_ALL_DAY_EVENT, 0);
//                    selStartHour = timePicker.getCurrentHour();
//                    selStartMin = timePicker.getCurrentMinute();

//                    int []values = Utilities.getMonthDayYearFromEpoch(getArguments().getLong(MainActivity.ARGS_DATE_INT));
//                    Calendar c = Calendar.getInstance();
//                    c.set(values[2], values[0]-1, values[1], timePicker.getCurrentHour(), timePicker.getCurrentMinute());
//                    long startTime = c.getTimeInMillis();
//                    Log.d("VALUEWDDDDDDDDDDDDDD ", " " + startTime);
//                    int startTimeInt = Utilities.dateToInt(startTime);
             //       getArguments().putLong(MainActivity.ARGS_BEGIN_TIME, startTime);
                    getArguments().putInt(MainActivity.ARGS_BEGIN_TIME_HOUR,timePicker.getCurrentHour());
                    getArguments().putInt(MainActivity.ARGS_BEGIN_TIME_MINUTE,timePicker.getCurrentMinute());


                    UntilFragment fragment = UntilFragment.newInstance(getArguments());
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }

            }
        });
        return  rootView;

    }


}
