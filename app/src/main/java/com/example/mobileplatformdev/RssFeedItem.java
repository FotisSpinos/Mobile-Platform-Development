package com.example.mobileplatformdev;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RssFeedItem {

    private String title;
    private Date endDate;
    private Point point;
    private ItemDescription itemDescription;

    // default constructor
    public RssFeedItem()
    {
        title = "NULL";
        itemDescription = new ItemDescription();
    }

    /* GETTERS*/

    public Point GetPoint()
    {
        return point;
    }

    public ItemDescription GetItemDescription() { return itemDescription; }

    public String GetTitle()
    {
        return title;
    }

    /* SETTERS */

    public void SetEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public void SetPoint(String point)
    {
        this.point = new Point(point);
    }

    public void SetTitle(String title)
    {
        this.title = title;
    }

    public void SetItemDescription(String description)
    {

    }

    public void SetItemDescription(ItemDescription itemDescription) { this.itemDescription = itemDescription; }

}
