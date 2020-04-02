package com.example.mobileplatformdev;

import android.util.Log;

import com.google.android.gms.maps.model.Marker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;

enum RssFeedItemType {
    INDIDENT,
    PLANNED_ROAD_WORK,
    ROADWORK
}


public class RssFeedItem {

    private String title;
    private Point point;
    private String summary;
    public Description description;

    private RssFeedItemType rssType;

    // default constructor
    public RssFeedItem(RssFeedItemType rssType)
    {
        title = "NULL";
        this.rssType = rssType;
    }

    public String GetMapDescription() {

        String type = "";
        String mapDescription = "";

        type = rssType.name().toLowerCase();

        switch(rssType){
            case PLANNED_ROAD_WORK:

                //DescriptionEntity tmpItem = (DescriptionEntity) description.GetItem("Works");

                if(mapDescription == null){
                    //tmpItem = (DescriptionEntity) description.GetItem("Type");
                }

                int counter = 2;

                mapDescription = "Starts at: ";
                mapDescription += description.GetItem("Start Date").toString();
                break;

            case ROADWORK:
                try {
                    mapDescription = "Ends at: ";
                    mapDescription += description.GetItem("End Date").toString();
                    //mapDescription += description.GetItem("Delay Information").toString();
                } catch (Exception e) {}
                break;

            case INDIDENT:
                try{
                    mapDescription = description.GetItem("Insident").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        return type + ": " + mapDescription;
    }

    /* GETTERS*/

    public Point GetPoint()
    {
        return point;
    }

    public String GetTitle()
    {
        return title;
    }

    public Object GetDescriptionElement(String tag) { return description.GetItem(tag); }

    public RssFeedItemType GetRssType() {return rssType;}

    /* SETTERS */

    public void SetPoint(String point)
    {
        this.point = new Point(point);
    }

    public void SetTitle(String title)
    {
        this.title = title;
    }

    public void SetItemDescription(Description description) { this.description = description; }
}
