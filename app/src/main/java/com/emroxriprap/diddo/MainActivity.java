package com.emroxriprap.diddo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String ARGS_EVENT_TITLE = "event_title";
    public static final String ARGS_WHAT = "what";
    public static final String ARGS_WITH = "with";
    public static final String ARGS_DATE_STRING = "date_string";
    public static final String ARGS_ALL_DAY_EVENT = "all_day_event";
    public static final String ARGS_BEGIN_TIME_HOUR = "begin_time_hour";
    public static final String ARGS_BEGIN_TIME_MINUTE = "begin_time_minute";
    public static final String ARGS_END_TIME_HOUR = "end_time_hour";
    public static final String ARGS_END_TIME_MINUTE = "end_time_minute";
    public static final String ARGS_DESCRIPTION = "description";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .add(R.id.container,new LogsListFragment())
                    .commit();
        }
    }
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 1){
//            getFragmentManager().popBackStack();
//            setTitle("Klok");
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new LogsListFragment())
                    .commit();
        }
        else if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else{
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
