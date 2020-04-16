package com.example.mobileplatformdev;

import com.google.android.gms.common.internal.Objects;

import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;

public class RssFeedItemSelector {

    private RssFeedItem desiredRssItem;
    private String desiredType;

    public static RssFeedItemSelector instance;

    private RssFeedItemSelector(){}

    public static RssFeedItemSelector GetInsrance(){
        if(instance == null){
            instance = new RssFeedItemSelector();
        }
        return instance;
    }

    public void SetDesiredRssItem(RssFeedItem rssFeedItem){
            this.desiredRssItem = rssFeedItem;
    }

    public void SetDesiredType(String desiredType){
        this.desiredType = desiredType;
    }

    public boolean isItemDesired(RssFeedItem checkItem){

        if(desiredRssItem == null || desiredType == "")
            return true;

        //compare the dates
        if(checkItem.GetDescription().GetItem("Start Date") != null) {
            Date desiredDate = (Date) desiredRssItem.GetDescription().GetItem("Start Date").GetValue();
            Date checkDate = (Date) checkItem.GetDescription().GetItem("Start Date").GetValue();
            if (desiredDate.after(checkDate))
                return false;
        }


        if(!DataHolder.GetInstance().GetTagFromRssItem(checkItem).equals(desiredType))
            return false;

        if(!checkItem.GetRssItemLocation().GetRoad().equals(desiredRssItem.GetRssItemLocation().GetRoad()))
                if(!desiredRssItem.GetRssItemLocation().GetRoad().equals(""))
            return false;

        LinkedList<String> junctions = checkItem.GetRssItemLocation().GetJunctions();

        if(desiredRssItem.GetRssItemLocation().GetJunctions().get(0).equals(""))
            return true;

        for(int i = 0; i < junctions.size(); i++){
            if(!junctions.get(i).equals(desiredRssItem.GetRssItemLocation().GetJunctions().get(0)))
                return true;
        }

        return true;
    }

    public String GetDesiredType() {
        return desiredType;
    }
}
