package com.example.mobileplatformdev;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemDescriptionFactory {
}
/*
    public static ItemDescription CreatePlannedRoadDesc(String xmlDesc) {

        // Create a new item description
        ItemDescription outputDesc = new ItemDescription();

        // Search for the end date and delay info index
        int endDateIndx = xmlDesc.indexOf(ItemDescriptionCategories.END_DATE);
        int delayInfoIndx = xmlDesc.indexOf(ItemDescriptionCategories.DELAY_INFORMATION);

        // Get the information from the found elements
        String endDateString = xmlDesc.substring(endDateIndx + ItemDescriptionCategories.END_DATE.length(),
                delayInfoIndx - ItemDescriptionCategories.BRT_XML.length() - 1);

        String delayInfoString = xmlDesc.substring(delayInfoIndx + ItemDescriptionCategories.DELAY_INFORMATION.length() + 1,
                xmlDesc.length() - 1);


        // Create the appropriate formater
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM YYYY - HH:mm");
        Date endDate = new Date();

        // try to parse the string date into a java date
        try {
            endDate = formatter.parse(endDateString);

            // return a planned road works description
            return new CurrentRoadworksDescription(endDate, delayInfoString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.println(Log.INFO, "rss item", "Failed to create element using the given data: \n" + xmlDesc);
        outputDesc.SetDescription(xmlDesc);

        // return the output description
        return outputDesc;
    }

    public static ItemDescription CreatePlannedRoadWorkDesc(String xmlDesc)
    {
        // Create a new item description
        ItemDescription outputDesc = new ItemDescription();

        // get the index of the space to seperate the two numbers
        int startDateIndx = xmlDesc.indexOf(ItemDescriptionCategories.START_DATE);
        int endDateIndx = xmlDesc.indexOf(ItemDescriptionCategories.END_DATE);
        int worksIndx = xmlDesc.indexOf(ItemDescriptionCategories.WORKS);

        String startDateString = xmlDesc.substring(startDateIndx + ItemDescriptionCategories.START_DATE.length()
                , endDateIndx - ItemDescriptionCategories.BRT_XML.length());

        String endDateString = xmlDesc.substring(endDateIndx + ItemDescriptionCategories.END_DATE.length(),
                worksIndx - ItemDescriptionCategories.BRT_XML.length());

        String worksString = xmlDesc.substring(worksIndx + ItemDescriptionCategories.WORKS.length(),
                xmlDesc.length() - 1);


        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM YYYY - HH:mm");
        Date startDate = new Date();
        Date endDate = new Date();

        // try to parse the dates
        try {
            startDate = formatter.parse(startDateString);
            endDate = formatter.parse(endDateString);
            // return a planned road works description
            return new PlannedRoadworksDescription(worksString, startDate, endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        outputDesc.SetDescription(xmlDesc);
        return outputDesc;
    }

    public static ItemDescription CreateInsidentDesc(String xmlDesc)
    {
        // Create a new item description
        ItemDescription outputDesc = new ItemDescription();

        outputDesc.SetDescription(xmlDesc);
        return outputDesc;
    }

    public static ItemDescription CreateItemDescription(String xmlDesc)
    {
        // Create a new item description
        ItemDescription outputDesc = new ItemDescription();

        // get the index of the space to seperate the two numbers
        int startDateIndx = xmlDesc.indexOf(ItemDescriptionCategories.START_DATE);
        int endDateIndx = xmlDesc.indexOf(ItemDescriptionCategories.END_DATE);
        int worksIndx = xmlDesc.indexOf(ItemDescriptionCategories.WORKS);
        int delayInfoIndx = xmlDesc.indexOf(ItemDescriptionCategories.DELAY_INFORMATION);

        // if the description matches a planned road work
        if(startDateIndx != -1 && endDateIndx != -1 && worksIndx != -1)
        {
            return CreatePlannedRoadWorkDesc(xmlDesc);
        }

        // if the description matches a current description
        else if(delayInfoIndx != -1)
        {
            return CreatePlannedRoadDesc(xmlDesc);
        }

        // if the description matches an insident
        else
        {
            return CreateInsidentDesc(xmlDesc);
        }
    }

}

 */