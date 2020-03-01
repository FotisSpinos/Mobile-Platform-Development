package com.example.mobileplatformdev;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
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
        setContentView(R.layout.activity_main);

        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

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

        //new Thread(new Task(roadworksSource)).start();
    } //

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
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
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

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    rawDataDisplay.setText(stringdata);
                }
            });


            LinkedList<RssFeedItem> insidents = RssFeedItemFactory.CreateRoadWorks(stringdata);
        }
    }

} // End of MainActivity