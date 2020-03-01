package com.example.mobileplatformdev;

import java.util.Date;

public class ItemDescription
{
    private String description;

    public void SetDescription(String description)
    {
        this.description = description;
    }

    public String GetDescription()
    {
        return description;
    }

    public Date GetStartDate()
    {
        return null;
    }

    public Date GetEndDate()
    {
        return null;
    }

    public void SetStartDate(Date date) { }

    public void SetEndDate(Date date) { }
}
