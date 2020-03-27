package com.example.mobileplatformdev;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

public class StoreRssItemsAsyncActivity extends AsyncTask<String, Integer, Hashtable<String, RssFeedItem>>
{
    private Hashtable<String, RssFeedItem> rssData;
    public String dataTag;

    @Override
    protected Hashtable<String, RssFeedItem> doInBackground(String... strings) {

        Log.e("started ", "thread started");

        URL aurl;
        URLConnection yc;
        BufferedReader in = null;
        String inputLine = "";
        String stringdata = "";

        try
        {
            aurl = new URL(strings[0]);
            yc = aurl.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            in.readLine();

            while ((inputLine = in.readLine()) != null)
            {
                stringdata = stringdata + inputLine;
            }
            in.close();
        }
        catch (IOException ae)
        {
            Log.e("IO Exception: ", "Unable to read data from source: " + strings[0].toString() + " " + ae.getLocalizedMessage());
        }

        // store the data
        RssDataLoaderHelper.GetRecordedData(stringdata);

        // store loaded data
        return rssData;
    }

    @Override
    protected void onPreExecute() {

        // instantiate rss data
        rssData = new Hashtable<String, RssFeedItem>();

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

    public Hashtable<String, RssFeedItem> GetLoadedData() {
        return rssData;
    }
}
