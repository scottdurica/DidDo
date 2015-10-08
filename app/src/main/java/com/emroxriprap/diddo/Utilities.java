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
}
