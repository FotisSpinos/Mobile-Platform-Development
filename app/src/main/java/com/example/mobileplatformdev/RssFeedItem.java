package com.example.mobileplatformdev;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RssFeedItem {

    private String title;
    private Point point;
    public Description description;

    // default constructor
    public RssFeedItem()
    {
        title = "NULL";

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
