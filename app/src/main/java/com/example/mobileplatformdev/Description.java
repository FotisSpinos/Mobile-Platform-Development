package com.example.mobileplatformdev;

import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class Description {
    //private HashMap<String, Object> descriptionEntities;
    private LinkedList<DescriptionEntity> descriptionEntities;

    // constructs an object using the description string
    public Description(String xmlDescriptionData) {
        descriptionEntities = new LinkedList<DescriptionEntity>();

        int lastDateIndex = StoreDates(xmlDescriptionData);

        // if no dates are found we can assume that the string represents an incident
        if(lastDateIndex == 0 &&
        descriptionEntities.isEmpty())
        {
            descriptionEntities.push(new DescriptionEntity("Insident", xmlDescriptionData));
            return;
        }

        StoreRemainingEntities(lastDateIndex, xmlDescriptionData);
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
                        //value = xmlDescriptionData.substring(y + 2, i + 1);    // the only difference, optimise this in a function?


                        value = ParseUtils.ParseToRSSDate(stringValue);

                        // add rss element
                        descriptionEntities.push(new DescriptionEntity(tag, value));
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
                        String stringValue = xmlDescriptionData.substring(y + 2, i);    // the only difference, optimise this in a function?

                        value = ParseUtils.ParseToRSSDate(stringValue);
                        // add rss element
                        descriptionEntities.push(new DescriptionEntity(tag, value));
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

        if (startIndex >= xmlDescriptionData.length())
            return;

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

                    tag = xmlDescriptionData.substring(i + 1, elementIndex + 1);
                    descriptionEntities.push(new DescriptionEntity(tag, value));

                    elementIndex = i - 1;
                    //elementIndex = i;
                    break;

                case ':':
                    if (!encounter) {
                        value = xmlDescriptionData.substring(i + 1, elementIndex + 1);  // elementIndex + 1
                        elementIndex = i - 1;
                        encounter = true;
                    } else if (encounter) {
                        tag = xmlDescriptionData.substring(i + 1, elementIndex + 1); // elementIndex + 1
                        //descriptionEntities.put(tag, value);

                        //change this later
                        value = new DescriptionEntity(tag, value);

                        elementIndex = i - 1;
                    }
            }
        }

        String debugDesc = xmlDescriptionData;
        //Log.println(Log.INFO,"debug tag", "data: " + xmlDescriptionData + "\nlength: " + xmlDescriptionData.length() + "\n from: " + Integer.toString(startIndex) + " to: " + Integer.toString(elementIndex));

        // add the final element
        tag = xmlDescriptionData.substring(startIndex, elementIndex + 1);


        descriptionEntities.push(new DescriptionEntity(tag, value));
    }

    public DescriptionEntity GetItem(String tag) {

        for(int i = 0; i < descriptionEntities.size(); i++) {
            if(descriptionEntities.get(i).GetTag().equals(tag))
                return descriptionEntities.get(i);
        }
        return null;
    }

    public DescriptionEntity GetItem(int index) {

        return descriptionEntities.get(index);
    }
}
