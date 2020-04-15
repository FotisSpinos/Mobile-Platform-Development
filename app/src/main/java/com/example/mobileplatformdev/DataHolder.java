package com.example.mobileplatformdev;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.Marker;

import java.util.*;

public class DataHolder {

    private ArrayList<Hashtable<String, RssFeedItem>> rssData;
    private ArrayList<String> rssTags;

    private Stack<StoreRssItemsAsyncActivity> storeActivities;
    private Stack<AddRssItemsToMapAsyncActivity> addToMapActivities;

    private static DataHolder instance;

    private DataHolder() {
        // instantiate rss linked list
        rssData = new ArrayList<Hashtable<String, RssFeedItem>>();
        rssTags = new ArrayList<String>();

        // init stack activities
        storeActivities = new Stack<StoreRssItemsAsyncActivity>();
        addToMapActivities = new Stack<AddRssItemsToMapAsyncActivity>();
    }

    public static DataHolder GetInstance() {
        if(instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public void StoreRssData(String url) {
        StoreRssItemsAsyncActivity storeRssItemsAsyncActivity = new StoreRssItemsAsyncActivity();
        storeRssItemsAsyncActivity.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);

        storeActivities.push(storeRssItemsAsyncActivity);
    }

    public void AddRssDataToFile(MapActivity mapActivity) {
        AddRssItemsToMapAsyncActivity addRssItemsToMapAsyncActivity = new AddRssItemsToMapAsyncActivity();
        addRssItemsToMapAsyncActivity.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mapActivity);

        addToMapActivities.push(addRssItemsToMapAsyncActivity);
    }

    public void AddRssData(String dataTag, Hashtable<String, RssFeedItem> item) {
        rssData.add(item);
        this.rssTags.add(dataTag);
    }

    public RssFeedItem GetRssItemWithPoint(Point point){
        RssFeedItem output = null;

        for(int i = 0; i < rssData.size(); i++) {
            output = rssData.get(i).get(point.ToString());

            if(output != null)
                return output;
        }

        return null;
    }

    public String GetTagFromRssItem(RssFeedItem rssFeedItem) {

        for (int i = 0; i < rssData.size(); i++){

            RssFeedItem item = rssData.get(i).get(rssFeedItem.GetPoint().ToString());

            if(item != null) {
                return rssTags.get(i);
            }
        }
        return "NOT FOUND";
    }

    public RssFeedItem GetRssItemFromMarker(Marker marker) {
        Point point = new Point(marker.getPosition().latitude, marker.getPosition().longitude);
        return GetRssItemWithPoint(point);
    }

    public void StopActivities(){
        while(!addToMapActivities.empty()){
            addToMapActivities.pop().cancel(true);
        }

        while(!storeActivities.empty()){
            storeActivities.pop().cancel(true);
        }
    }

    public void RefreshData(String[] urls)
    {
        StopActivities();

        rssData.clear();
        rssTags.clear();

        for(int i = 0; i < urls.length; i++) {
            StoreRssData(urls[i]);
        }
    }

    public ArrayList<String> GetTags() {
        return rssTags;
    }

    public ArrayList<Hashtable<String, RssFeedItem>> GetRssData() {
        return rssData;
    }
}