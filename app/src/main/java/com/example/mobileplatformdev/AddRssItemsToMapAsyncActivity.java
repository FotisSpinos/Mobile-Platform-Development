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

        try{
            while(DataHolder.GetInstance().GetRssData().size() != 3) {
                Log.e("Not ready", "data have not being loaded sleep for 5000 " + DataHolder.GetInstance().GetRssData().size());
                Thread.sleep(5000);
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

        for(int i = 0; i < DataHolder.GetInstance().GetRssData().size(); i++){

            Set<String> keys = DataHolder.GetInstance().GetRssData().get(i).keySet();

            for(String key: keys){
                RssFeedItem item = DataHolder.GetInstance().GetRssData().get(i).get(key);
                mapActivity.AddMapPoint(item);
            }
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
