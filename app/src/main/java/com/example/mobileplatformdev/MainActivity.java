package com.example.mobileplatformdev;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

//AppCompatActivity implements SettingFragment.FragmentSettingsListener
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    // Traffic Scotland URLs
    private String roadworksSource = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String plannedRoadworksSource = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private String InsidentsSource = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";

    RssDataLoader insidentsLoader;
    RssDataLoader plannedRoadworksLoader;
    RssDataLoader roadworksLoader;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(MainActivity.this, mapActivity.class);
        startActivity(intent);

        // instantiate loaders
        insidentsLoader = new RssDataLoader(InsidentsSource);
        plannedRoadworksLoader = new RssDataLoader(plannedRoadworksSource);
        roadworksLoader = new RssDataLoader(roadworksSource);

        startProgress();
    }

    @Override
    public void onClick(View aview)
    {
        startProgress();
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(insidentsLoader).start();
        new Thread(plannedRoadworksLoader).start();
        new Thread(roadworksLoader).start();
    }

    private class RssDataLoader implements Runnable
    {
        private LinkedList<RssFeedItem> loadedData;
        private String url;

        public RssDataLoader(String aurl)
        {
            url = aurl;
            loadedData = new LinkedList<RssFeedItem>();
        }

        @Override
        public void run()
        {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";
            String stringdata = "";

            try
            {
                aurl = new URL(url);
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
                Log.e("IO Exception: ", "Unable to read data from source: " + url.toString() + " " + ae.getLocalizedMessage());
            }

            loadedData = RssFeedItemFactory.CreateRoadWorks(stringdata);
        }

        /* GETTERS */

        public LinkedList<RssFeedItem> GetLoadedData()
        {
            return loadedData;
        }

        public String GetURL()
        {
            return url;
        }
    }
}




























    /*
    private MapFragment fragmentMap;
    private String settings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = "TFFFF";

        fragmentMap = new MapFragment();
        //fragmentSetting = new SettingFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_a, fragmentMap)
                .commit();
    }

}


































    /*
    private TextView rawDataDisplay;
    private String stringdata;
    private Button startButton;

    // Traffic Scotland URLs
    private String roadworksSource = "https://trafficscotland.org/rss/feeds/roadworks.aspx";

    
    private String plannedRoadworksSource = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";

    private String InsidentsSource = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        //startButton = (Button)findViewById(R.id.startButton);
        //startButton.setOnClickListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);

    }

    public void onClick(View aview)
    {
        startProgress();
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
         new Thread(new Task(InsidentsSource)).start();
         new Thread(new Task(plannedRoadworksSource)).start();

        new Thread(new Task(roadworksSource)).start();
    } //

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {
            stringdata = "";
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
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
                Log.e("IO Exception: ", "Unable to read data from source: " + url.toString() + " " + ae.getLocalizedMessage());
            }

            LinkedList<RssFeedItem> insidents = RssFeedItemFactory.CreateRoadWorks(stringdata);
        }
    }

} // End of MainActivity */