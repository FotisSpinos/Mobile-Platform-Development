package com.example.mobileplatformdev;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.Marker;

import java.util.Date;

public class DetailedInfoFragment extends Fragment {

    private RssFeedItem rssFeedItem;
    private ViewGroup container;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rss_map_description, container, false);
        this.container = container;
        view = v;
        show();
        return v;
    }

    public void setRssFeedItem(RssFeedItem rssFeedItem) {
        this.rssFeedItem = rssFeedItem;
    }

    public void show(Marker rssFeedItem) {
        setRssFeedItem(DataHolder.GetInstance().GetRssItemFromMarker(rssFeedItem));
        show();
    }

    public void show() {

        // set title
        TextView titleText = (TextView) view.findViewById(R.id.description_title);

        // define title text formatting
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        layoutParams.setMargins(0, 40, 0, 0);
        titleText.setLayoutParams(layoutParams);

        titleText.setGravity(Gravity.CENTER_HORIZONTAL);
        titleText.setTextSize(30);
        titleText.setTextColor(Color.WHITE);

        Description desc;

        // add sections according to types
        String tag = DataHolder.GetInstance().GetTagFromRssItem(rssFeedItem);

        // add road to the description
        addSection("Road", rssFeedItem.GetRssItemLocation().GetRoad());

        switch (tag){
            case RssFeedTypes.CURRENT_INSIDENT:
                titleText.setText(RssFeedTypes.CURRENT_INSIDENT);

                desc = rssFeedItem.GetDescription();
                if(desc.GetItem(0) != null) {

                    addSection("Description", desc.GetItem(0).GetValue().toString());
                }

                break;
            case RssFeedTypes.PLANNED_ROADWORK:
                titleText.setText(RssFeedTypes.PLANNED_ROADWORK);

                desc = rssFeedItem.GetDescription();

                addSection("Start date", desc.GetItem("Start Date").GetValue().toString());
                addSection("End date", desc.GetItem("End Date").GetValue().toString());

                if(desc.GetItem(0) != null && !desc.GetItem("Start Date").GetValue().equals(desc.GetItem(0).GetValue())) {
                    DescriptionEntity tmp = (DescriptionEntity)(desc.GetItem(0).GetValue());
                    addSection("Status", tmp.GetValue().toString());
                    addSection("Details", tmp.GetTag());
                }
                break;

            case RssFeedTypes.ROADWORK:
                titleText.setText("Current " + RssFeedTypes.ROADWORK);

                desc = rssFeedItem.GetDescription();

                addSection("Start date", desc.GetItem("Start Date").GetValue().toString());
                addSection("End date", desc.GetItem("End Date").GetValue().toString());

                if(desc.GetItem(0) != null)
                    addSection(desc.GetItem(0).GetTag().toString(), desc.GetItem(0).GetValue().toString());
                break;
        }
    }

    public void addSection(String title, String contents) {

        // find descriptionRootLayout
        LinearLayout descriptionLayout = (LinearLayout) view.findViewById(R.id.description_linear_layout);

        // create description layout
        LinearLayout sectionLayout = new LinearLayout(view.getContext());
        //sectionLayout.setRight(500);

        sectionLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                500, 200);


        layoutParams.setMargins(30, 0, 30, 0);


        // define layout params
        //ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(500, 200);

        // adds and returns view after adding tet view to the section layout
        sectionLayout = addTextViewToSection(title, layoutParams, sectionLayout);
        sectionLayout = addTextViewToSection(contents, layoutParams, sectionLayout);

        // add view to description layout
        descriptionLayout.addView(sectionLayout);
    }

    public LinearLayout addTextViewToSection(String contents, ViewGroup.LayoutParams layoutParams, LinearLayout currentLayout){
        TextView titleTextView = new TextView(container.getContext());
        titleTextView.setText(contents);
        titleTextView.setTextSize(20);
        titleTextView.setTextColor(Color.WHITE);

        currentLayout.addView(titleTextView, layoutParams);

        return currentLayout;
    }
}

/*
        Button myButton = new Button(container.getContext());
        myButton.setText("Add Me");
        LinearLayout ll = (LinearLayout ) view.findViewById(R.id.linearLayout);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);
        */

