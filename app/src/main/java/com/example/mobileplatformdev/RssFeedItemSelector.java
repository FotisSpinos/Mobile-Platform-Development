package com.example.mobileplatformdev;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class RssFeedItemSelector {

    private RssFeedItem desiredRssItem;
    private ArrayList<String> desiredTypes;

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

    public void SetDesiredType(ArrayList<String> desiredType){
        this.desiredTypes = desiredType;
    }

    public boolean IsMatchingItemType(String type){
        boolean matchingType = false;
        for(int i = 0; i < desiredTypes.size(); i++){
            if(desiredTypes.get(i).equals(type)){
                matchingType = true;
            }
        }

        return matchingType;
    }

    public boolean isItemDesired(RssFeedItem checkItem){

        if(desiredRssItem == null)  //|| desiredType == ""
            return true;


        //compare the dates
        if(checkItem.GetDescription().GetItem("Start Date") != null) {
            Date desiredDate = (Date) desiredRssItem.GetDescription().GetItem("Start Date").GetValue();
            Date checkDate = (Date) checkItem.GetDescription().GetItem("Start Date").GetValue();
            if (desiredDate.after(checkDate))
                return false;
        }


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

    public ArrayList<String> GetDesiredType() {
        return desiredTypes;
    }
}
