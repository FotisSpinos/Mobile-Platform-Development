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

        try{
            while(DataHolder.GetInstance().GetRssData().size() != ApplicationVariables.MAX_RSS_CATEGORIES) {
                Thread.sleep(3000);

                if(startSize != DataHolder.GetInstance().GetRssData().size()) {
                    mapActivities[0].runOnUiThread(new AddRssItemsToMapRunnable(DataHolder.GetInstance().GetRssData().
                            get(DataHolder.GetInstance().GetRssData().size() - 1)));

                    startSize = DataHolder.GetInstance().GetRssData().size();
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
    protected void onPostExecute(Hashtable<String, RssFeedItem> stringRssFeedItemHashtable) {
        super.onPostExecute(stringRssFeedItemHashtable);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    class AddRssItemsToMapRunnable implements Runnable{

        private Hashtable<String, RssFeedItem> rssItems;

        public AddRssItemsToMapRunnable(Hashtable<String, RssFeedItem> rssItems) {
            this.rssItems = rssItems;
        }

        @Override
        public void run() {
            Set<String> keys = rssItems.keySet();

            for(String key: keys) {
                RssFeedItem item = rssItems.get(key);
                mapActivity.AddMapPoint(item);
            }
        }
    }
}
