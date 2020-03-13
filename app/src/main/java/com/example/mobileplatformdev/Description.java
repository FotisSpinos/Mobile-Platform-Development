package com.example.mobileplatformdev;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class Description {
    // the tags supported
    private String[] supportedTags;

    // TEMP REMOVE LATER
    public String xmlDescriptionData;

    private HashMap<String, Object> descriptionEntities;

    // constructs an object using the description string
    public Description(String xmlDescriptionData) {
        descriptionEntities = new HashMap<String, Object>();

        int lastDateIndex = StoreDates(xmlDescriptionData);

        StoreRemainingEntities(lastDateIndex, xmlDescriptionData);

        this.xmlDescriptionData = xmlDescriptionData;
    }

    // Stores description dates and returns the last index of the string read by the function
    private int StoreDates(String xmlDescriptionData) {
        String tag;
        Object value;

        int elementIndex = 0;
        int dateCounter = 0;

        for (int i = 0; i < xmlDescriptionData.length(); i++) {
            if (i == xmlDescriptionData.length() - 1) {
                for (int y = elementIndex; y < i; y++) {
                    if (xmlDescriptionData.charAt(y) == ':') {
                        //store tag and value
                        tag = xmlDescriptionData.substring(elementIndex, y);
                        String stringValue = xmlDescriptionData.substring(y + 2, i + 1);
                        value = xmlDescriptionData.substring(y + 2, i + 1);    // the only difference, optimise this in a function?


                        // parse string to date
                        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM YYYY - HH:mm");
                        Date endDate = new Date();

                        try {
                            value = formatter.parse(stringValue);

                            // return a planned road works description


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // add rss element
                        descriptionEntities.put(tag, value);
                        elementIndex = i + 6;

                        dateCounter++;

                        //! stop reading the xml data
                        return elementIndex;
                    }
                }
            } else if (xmlDescriptionData.charAt(i) == '<') {
                for (int y = elementIndex; y < i; y++) {
                    if (xmlDescriptionData.charAt(y) == ':') {
                        //store tag and value
                        tag = xmlDescriptionData.substring(elementIndex, y);
                        value = xmlDescriptionData.substring(y + 2, i);    // the only difference, optimise this in a function?

                        // add rss element
                        descriptionEntities.put(tag, value);
                        elementIndex = i + 6;

                        dateCounter++;

                        //! stop reading the xml data
                        break;
                    }
                }
            }

            if (dateCounter == 2)
                break;
        }

        return elementIndex;
    }

    // stores the remaining entities found between the start index and the end of the string
    private void StoreRemainingEntities(int startIndex, String xmlDescriptionData) {
        int elementIndex = xmlDescriptionData.length() - 1;
        Object value = "";
        String tag = "";
        boolean encounter = false;

        for (int i = xmlDescriptionData.length() - 1; i >= startIndex; i--) {
            switch (xmlDescriptionData.charAt(i)) {
                case '.':
                    // this is the start character it does not indicates categories
                    if (i == xmlDescriptionData.length() - 1)
                        continue;
                    encounter = false;

                    Log.println(Log.INFO,"debug tag", "data: " + xmlDescriptionData + "\nlength: " + xmlDescriptionData.length() + "\n from: " + Integer.toString(i) + " to: " + Integer.toString(elementIndex));

                    tag = xmlDescriptionData.substring(i, elementIndex);
                    descriptionEntities.put(tag, value);

                    elementIndex = i - 1;
                    break;

                case ':':
                    if (!encounter) {
                        value = xmlDescriptionData.substring(i + 2, elementIndex + 1);
                        elementIndex = i - 1;
                        encounter = true;
                    } else if (encounter) {
                        tag = xmlDescriptionData.substring(i + 2, elementIndex + 1);
                        descriptionEntities.put(tag, value);

                        //change this later
                        value = new DescriptionEntity(tag, value);

                        elementIndex = i - 1;
                    }
            }
        }

        // add the final element
        tag = xmlDescriptionData.substring(startIndex, elementIndex);

        descriptionEntities.put(tag, value);
    }

    public Object GetItem(String tag)
    {
        return descriptionEntities.get(tag);
    }
}
