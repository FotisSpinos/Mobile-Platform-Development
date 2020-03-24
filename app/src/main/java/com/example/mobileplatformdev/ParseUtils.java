package com.example.mobileplatformdev;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseUtils {

    private ParseUtils(){}

    public static Date ParseToRSSDate(String stringDate)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM YYYY - HH:mm");
        Date outputDate = new Date();

        try {
            outputDate = formatter.parse(stringDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDate;
    }
}
