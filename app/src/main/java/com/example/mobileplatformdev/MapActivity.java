package com.example.mobileplatformdev;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMarkerClickListener {

    // google map instance
    private GoogleMap googleMap;

    // search view instance
    private SearchView searchView;

    private ImageButton settingsButton;
    private Button locationButton;
    private Button infoButton;
    private DetailedInfoFragment descFragment;


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

        searchView = (SearchView) findViewById(R.id.search_view);

        // create description fragment
        descFragment = new DetailedInfoFragment();

    }

    public void RefreshMapData() {
        googleMap.clear();

        // refresh data
        String urls[] = {RssUrl.INSIDENTS_URL, RssUrl.ROADWORKS_URL, RssUrl.PLANNED_ROADWORKS_URL};

        DataHolder.GetInstance().RefreshData(urls);

        // add rss data to the map once they have been stored
        AddRssItemsToMapAsyncActivity addRssItemsToMapAsyncActivity = new AddRssItemsToMapAsyncActivity();
        addRssItemsToMapAsyncActivity.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Move camera to Glasgow
        LatLng glasgow = new LatLng(55.861, -4.25);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(glasgow, 5));

        // refresh data
        RefreshMapData();

        // set this activity as the map's OnMarkerClickListener
        googleMap.setOnMarkerClickListener(this);

        searchView.setOnQueryTextListener(new GoogleMapTextListener(this, googleMap, searchView));
    }

    public void AddMapPoint(RssFeedItem item)
    {
        LatLng itemLocation = new LatLng(item.GetPoint().GetX(), item.GetPoint().GetY());

        Marker marker = this.googleMap.addMarker(new MarkerOptions().position(itemLocation));

        marker.setTitle(item.DefineMapDescription());

        String tag = DataHolder.GetInstance().GetTagFromRssItem(item);

        float markerColor = BitmapDescriptorFactory.HUE_VIOLET;

        if(tag.equals(RssFeedTypes.CURRENT_INSIDENT))
            markerColor = BitmapDescriptorFactory.HUE_RED;
        else if(tag.equals(RssFeedTypes.PLANNED_ROADWORK))
            markerColor = BitmapDescriptorFactory.HUE_GREEN;
        else if(tag.equals(RssFeedTypes.ROADWORK))
            markerColor = BitmapDescriptorFactory.HUE_YELLOW;

        marker.setIcon(BitmapDescriptorFactory.defaultMarker(markerColor));
    }

    public GoogleMap GetGoogleMap()
    {
        return googleMap;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.settings_button:
                //RefreshMapData();

                Intent myIntent = new Intent(this, SearchActivity.class);
                this.startActivity(myIntent);
                break;

            case R.id.rss_info_button:
                //googleMap.clear();

                //RssWindowAdapter windowAdapter = new RssWindowAdapter(MapActivity.this);
                //googleMap.setInfoWindowAdapter(windowAdapter);

                //windowAdapter.ShowRssInfo(currentMarker, findViewById(R.id.map));
                //windowAdapter.ShowRssInfo();

                //replaces fragment
                if(currentMarker == null)
                    break;

                if(!descFragment.isVisible()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.description_container, descFragment)
                            .commit();
                    descFragment.setRssFeedItem( DataHolder.GetInstance().GetRssItemWithPoint(new Point(currentMarker.getPosition().latitude, currentMarker.getPosition().longitude)));
                }
                else
                    getSupportFragmentManager().beginTransaction().remove(descFragment).commit();


                if(currentMarker == null)
                    return;

                //RssFeedItem item = DataHolder.GetInstance().GetRssItemWithPoint(new Point(currentMarker.getPosition().latitude, currentMarker.getPosition().longitude));
                break;

            case R.id.user_location_button:
                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // if the fragment is visible remove it
        if(descFragment.isVisible())
            getSupportFragmentManager().beginTransaction().remove(descFragment).commit();

        // store the selected marker
        currentMarker = marker;
        return false;
    }
}
