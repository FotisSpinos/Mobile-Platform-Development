package com.example.mobileplatformdev;

import java.util.Date;

public class CurrentRoadworksDescription extends ItemDescription
{
    private String delayInfo;
    private Date endDate;

    public CurrentRoadworksDescription(Date endDate, String delayInfo)
    {
        this.description = "No description provided";
        this.endDate = endDate;
        this.delayInfo = delayInfo;
    }

    public Date GetEndDate() { return endDate; }

    public void SetStartDate(Date endDate) {this.endDate = endDate;}
}
