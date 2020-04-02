package com.example.mobileplatformdev;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMarkerClickListener {

    // google map instance
    private GoogleMap googleMap;

    private ImageButton settingsButton;
    private Button locationButton;
    private Button infoButton;

    private Marker currentMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        // set map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        settingsButton = (ImageButton) findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(this);

        locationButton = (Button) findViewById(R.id.user_location_button);
        locationButton.setOnClickListener(this);

        infoButton = (Button) findViewById(R.id.rss_info_button);
        infoButton.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Move camera to Glasgow
        LatLng glasgow = new LatLng(55.861, -4.25);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(glasgow));

        // load rss data
        DataHolder.GetInstance().StoreRssData(RssUrl.INSIDENTS_URL);
        DataHolder.GetInstance().StoreRssData(RssUrl.ROADWORKS_URL);
        DataHolder.GetInstance().StoreRssData(RssUrl.PLANNED_ROADWORKS_URL);

        // add rss data to the map once they have been stored
        AddRssItemsToMapAsyncActivity addRssItemsToMapAsyncActivity = new AddRssItemsToMapAsyncActivity();
        addRssItemsToMapAsyncActivity.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this);
    }

    public void AddMapPoint(RssFeedItem item)
    {
        LatLng itemLocation = new LatLng(item.GetPoint().GetX(), item.GetPoint().GetY());

        Marker marker = this.googleMap.addMarker(new MarkerOptions().position(itemLocation));

        marker.setTitle(item.GetMapDescription());
    }

    public GoogleMap GetGoogleMap()
    {
        return googleMap;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.settings_button:
                break;

            case R.id.rss_info_button:
                //googleMap.clear();
                RssWindowAdapter windowAdapter = new RssWindowAdapter(MapActivity.this);
                googleMap.setInfoWindowAdapter(windowAdapter);

                //windowAdapter.ShowRssInfo(currentMarker, findViewById(R.id.map));
                //windowAdapter.ShowRssInfo();
                break;

            case R.id.user_location_button:

                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        currentMarker = marker;
        return true;
    }
}
