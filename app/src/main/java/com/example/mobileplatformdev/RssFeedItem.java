package com.example.mobileplatformdev;

import java.util.Date;

public class RssFeedItem {

    private String title;
    private Date endDate;
    private Point point;
    private ItemDescription description;

    // default constructor
    public RssFeedItem()
    {
        description = new ItemDescription();
    }

    /* GETTERS*/

    public Point GetPoint()
    {
        return point;
    }

    public ItemDescription GetItemDescription()
    {
        return description;
    }

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
        this.description.SetDescription(description);
    }

    public void SetItemDescription(String description, Date startDate)
    {
        this.description.SetDescription(description);
    }

    public void SetItemDescription(String description, Date startDate, Date endDate)
    {
        this.description.SetDescription(description);
    }
}
