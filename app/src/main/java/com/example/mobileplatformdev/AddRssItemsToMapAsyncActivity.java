package com.example.mobileplatformdev;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;
public class AddRssItemsToMapAsyncActivity extends AsyncTask<MapActivity, Integer, LinkedList<RssFeedItem>> {

    private MapActivity mapActivity;

    @Override
    protected LinkedList<RssFeedItem> doInBackground(MapActivity... mapActivities) {

        mapActivity = mapActivities[0];

        int startSize = DataHolder.GetInstance().GetRssData().size();
        int index = 0;

        try{
            while(DataHolder.GetInstance().GetRssData().size() < ApplicationVariables.MAX_RSS_CATEGORIES) {  //ApplicationVariables.MAX_RSS_CATEGORIES
                Thread.sleep(3000);

                if(startSize != DataHolder.GetInstance().GetRssData().size()) {

                    mapActivities[0].runOnUiThread(
                            new AddRssItemsToMapRunnable(DataHolder.GetInstance().GetRssData().get(startSize)));

                    startSize = DataHolder.GetInstance().GetRssData().size();
                    index++;


                    Log.e("Add to map", "Adding data to map");
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(LinkedList<RssFeedItem> stringRssFeedItemHashtable) {
        super.onPostExecute(stringRssFeedItemHashtable);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    class AddRssItemsToMapRunnable implements Runnable{

        private LinkedList<RssFeedItem> rssItems;

        public AddRssItemsToMapRunnable(LinkedList<RssFeedItem> rssItems) {
            this.rssItems = rssItems;
        }

        @Override
        public void run() {

            for(RssFeedItem item: rssItems) {
                mapActivity.AddMapPoint(item);
            }
        }
    }
}
