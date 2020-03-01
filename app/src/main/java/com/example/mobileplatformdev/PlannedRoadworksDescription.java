package com.example.mobileplatformdev;

import java.util.Date;

public class PlannedRoadworksDescription extends ItemDescription
{
    private Date startDate;
    private Date endDate;
    private String description;

    PlannedRoadworksDescription(String descreption, Date startDate, Date endDate)
    {
        this.description = descreption;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
