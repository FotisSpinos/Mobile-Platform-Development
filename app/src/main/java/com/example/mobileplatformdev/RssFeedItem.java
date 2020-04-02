package com.example.mobileplatformdev;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class RssFeedItem {

    private String title;
    private Point point;
    private String summary;

    public Description description;

    // default constructor
    public RssFeedItem()
    {
        title = "NULL";

    }

    public String GetMapDescription() {

        String type = "";
        String mapDescription = "";

        ArrayList<Hashtable<String, RssFeedItem>> data = DataHolder.GetInstance().GetRssData();

        // define the type
        for (int i = 0; i < data.size(); i++){

            RssFeedItem item = data.get(i).get(title);

            if(item != null) {
                type = DataHolder.GetInstance().GetTags().get(i);
                break;
            }
        }

        switch(type){
            case "Planned Roadwork":

                //DescriptionEntity tmpItem = (DescriptionEntity) description.GetItem("Works");

                if(mapDescription == null){
                    //tmpItem = (DescriptionEntity) description.GetItem("Type");
                }

                int counter = 2;

                mapDescription = "Starts at: ";
                mapDescription += description.GetItem("Start Date").toString();//tmpItem.GetValue().toString();

                break;

            case "Roadwork":
                try {
                    mapDescription = "Ends at: ";
                    mapDescription += description.GetItem("End Date").toString();
                    //mapDescription += description.GetItem("Delay Information").toString();
                } catch (Exception e) {}
                break;

            case "Current Incident":
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
