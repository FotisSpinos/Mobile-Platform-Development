package com.example.mobileplatformdev;

import java.util.Date;

public class Insidents
{
    private String title;
    private Date endDate;
    private Point point;
    private String description;

    // default constructor
    public Insidents()
    {

    }

    /* GETTERS*/

    public Point GetPoint()
    {
        return point;
    }

    public String GetDescription()
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

    public void SetDescription(String description)
    {
        this.description = description;
    }


}
