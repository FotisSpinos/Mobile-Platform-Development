package com.example.mobileplatformdev;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseUtils {

    private ParseUtils(){}

    public static Date ParseToRSSDate(String stringDate)
    {

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy - HH:mm" ); //, dd MMM YYYY - HH:mm
        Date outputDate = new Date();

        try {
            outputDate = dateFormat.parse(stringDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDate;
    }
}
