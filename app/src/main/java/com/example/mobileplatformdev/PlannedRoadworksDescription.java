package com.example.mobileplatformdev;

import java.util.Date;

public class PlannedRoadworksDescription extends ItemDescription
{
    private Date startDate;
    private Date endDate;

    PlannedRoadworksDescription(String description, Date startDate, Date endDate)
    {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date GetStartDate()
    {
        return startDate;
    }

    public Date GetEndDate()
    {
        return endDate;
    }

    public void SetStartDate(Date startDate) { this.startDate = startDate;}

    public void SetEndDate(Date endDate) { this.endDate = endDate; }
}
