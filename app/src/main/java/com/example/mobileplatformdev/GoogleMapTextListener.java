package com.example.mobileplatformdev;

import android.location.Address;
import android.location.Geocoder;
import androidx.appcompat.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class GoogleMapTextListener implements SearchView.OnQueryTextListener {

    private MapActivity mapActivity;
    private GoogleMap googleMap;
    private SearchView searchView;

    public GoogleMapTextListener(MapActivity mapActivity, GoogleMap googleMap, SearchView searchView){
        this.searchView = searchView;
        this.googleMap = googleMap;
        this.mapActivity = mapActivity;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String location = this.searchView.getQuery().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(mapActivity);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        }
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
