package com.example.mobileplatformdev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class RssWindowAdapter  implements GoogleMap.InfoWindowAdapter {

    private View window;
    private Context context;

    public RssWindowAdapter(Context context){
        context = context;
        window = LayoutInflater.from(context).inflate(R.layout.rss_info_window, null);
    }

    private void ShowRssInfo(Marker marker, View view) {
        String title = marker.getTitle();
        TextView textView = (TextView) view.findViewById(R.id.rss_title);

        textView.setText(title);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        ShowRssInfo(marker, window);
        return window;
    }

    @Override
    public View getInfoContents(Marker marker) {
        ShowRssInfo(marker, window);
        return window;
    }
}
