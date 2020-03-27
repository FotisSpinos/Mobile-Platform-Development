package com.example.mobileplatformdev;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    // google map instance
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        // set map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // requires permission
        //LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 10, (LocationListener) locationManager);

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

        this.googleMap.addMarker(new MarkerOptions().position(itemLocation).
                title(item.GetMapDescription()));
    }

    public GoogleMap GetGoogleMap()
    {
        return googleMap;
    }
}
