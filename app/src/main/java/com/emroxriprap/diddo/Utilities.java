package com.emroxriprap.diddo;

import java.text.SimpleDateFormat;

/**
 * Created by Scott Durica on 10/4/2015.
 */
public class Utilities {

    public static String dateToString (long longDate){
        String dateAsText = new SimpleDateFormat("MM-dd-yyyy").format(longDate);
        return dateAsText;
    }
    public static int dateToInt(long longDate){
        long l = longDate/1000;
        int returnDate = (int)l;
        return returnDate;
    }
    public static long intToLong(int i){
        long value = (long)i;
        return value;
    }
    public static int[]getMonthDayYearFromDateString(String s){
        int [] values = new int[3];
        int year = Integer.parseInt(s.substring(6));
        int month = Integer.parseInt(s.substring(0,2));
        int day = Integer.parseInt(s.substring(3,5));
        values[0] = month;
        values[1] = day;
        values[2] = year;
        return values;
    }
    public static int []getMonthDayYearFromEpoch(long date){
        int [] values = new int[3];
        long l = date * 1000;
        String s = dateToString(l);
        int year = Integer.parseInt(s.substring(6));
        int month = Integer.parseInt(s.substring(0,2));
        int day = Integer.parseInt(s.substring(3,5));
        values[0] = month;
        values[1] = day;
        values[2] = year;
        return values;
    }
}
