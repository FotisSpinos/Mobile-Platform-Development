package com.example.mobileplatformdev;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Hashtable;
import java.util.Set;

public class AddRssItemsToMapAsyncActivity extends AsyncTask<MapActivity, Integer, Hashtable<String, RssFeedItem>> {

    private MapActivity mapActivity;

    @Override
    protected Hashtable<String, RssFeedItem> doInBackground(MapActivity... mapActivities) {

        mapActivity = mapActivities[0];

        int startSize = DataHolder.GetInstance().GetRssData().size();

        if(startSize == ApplicationVariables.MAX_RSS_CATEGORIES){
            for(int i = 0; i < startSize; i++) {
                if(RssFeedItemSelector.GetInstance().GetDesiredType() == null){
                    AddItemCollectionToMap(DataHolder.GetInstance().GetRssData().get(i));
                }
                else if(RssFeedItemSelector.GetInstance().GetDesiredType() != null &&
                        RssFeedItemSelector.GetInstance().IsMatchingItemType(DataHolder.GetInstance().GetTags().get(i))){
                    AddItemCollectionToMap(DataHolder.GetInstance().GetRssData().get(i));
                }

            }
        }

        try{
            while(DataHolder.GetInstance().GetRssData().size() != ApplicationVariables.MAX_RSS_CATEGORIES) {
                Thread.sleep(1 * 1000);

                if(startSize != DataHolder.GetInstance().GetRssData().size()) {

                    AddRssItemsToMapRunnable addRssItemsToMapRunnable = new AddRssItemsToMapRunnable(
                            DataHolder.GetInstance().GetRssData().
                            get(DataHolder.GetInstance().GetRssData().size() - 1));

                    mapActivities[0].runOnUiThread(addRssItemsToMapRunnable);

                    startSize = DataHolder.GetInstance().GetRssData().size();
                    Log.e("Add to map", "Adding data to map");
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void AddItemCollectionToMap(Hashtable<String, RssFeedItem> rssItems){
        AddRssItemsToMapRunnable addRssItemsToMapRunnable = new AddRssItemsToMapRunnable(rssItems);
        mapActivity.runOnUiThread(addRssItemsToMapRunnable);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(Hashtable<String, RssFeedItem> stringRssFeedItemHashtable) {
        super.onPostExecute(stringRssFeedItemHashtable);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    class AddRssItemsToMapRunnable implements Runnable{

        private Hashtable<String, RssFeedItem> rssItems;
        private boolean stop;

        public AddRssItemsToMapRunnable(Hashtable<String, RssFeedItem> rssItems) {
            this.rssItems = rssItems;
            stop = false;
        }

        public void StopThread(){

        }

        @Override
        public void run() {
            Log.e("Add to map", "Adding data to map thread started");
            Set<String> keys = rssItems.keySet();

            for(String key: keys) {
                RssFeedItem item = rssItems.get(key);

                if(RssFeedItemSelector.GetInstance().isItemDesired(item))
                {
                    mapActivity.AddMapPoint(item);
                }
            }
        }
    }
}
