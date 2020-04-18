package com.example.mobileplatformdev;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileplatformdev.R;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.*;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, OnItemSelectedListener {

    private CheckBox incidentCheckBox;
    private CheckBox plannedRoadworksCheckBox;
    private CheckBox roadworksCheckBox;

    private Spinner daySpinner;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private Button applyButton;

    private EditText roadEditText;
    private EditText junctionEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        incidentCheckBox = (CheckBox) findViewById(R.id.incidents_checkBox);
        plannedRoadworksCheckBox = (CheckBox) findViewById(R.id.planned_roadworks_checkbox);
        roadworksCheckBox = (CheckBox) findViewById(R.id.current_road_works_checkbox);

        daySpinner = (Spinner) findViewById(R.id.day_spinner);
        monthSpinner = (Spinner) findViewById(R.id.month_spinner);
        yearSpinner = (Spinner) findViewById(R.id.year_spinner);
        applyButton = (Button) findViewById(R.id.apply_button);
        roadEditText = (EditText) findViewById(R.id.road_edit_text);
        junctionEditText = (EditText) findViewById(R.id.junction_edit_text);

        // set on click listener for apply button
        applyButton.setOnClickListener(this);

        monthSpinner.setOnItemSelectedListener(this);

        // add elements to type spinner
        ArrayList<String> arrayList = new ArrayList<>(3);
        arrayList.add(0, RssFeedTypes.CURRENT_INSIDENT);
        arrayList.add(1, RssFeedTypes.ROADWORK);
        arrayList.add(2, RssFeedTypes.PLANNED_ROADWORK);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //typeSpinner.setAdapter(arrayAdapter);
        //typeSpinner.setOnItemSelectedListener(this);

        // add elements to day spinner
        ArrayAdapter<Integer> dayAdapter = new DaySpinnerContainer(1, this).GetSpinnerData();
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        // add elements to month spinner
        ArrayAdapter<Byte> monthAdapter = new MonthSpinnerContainer(this).GetSpinnerData();
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        // add elements to year spinner
        ArrayAdapter<Integer> yearAdapter = new YearSpinnerDataContainer(2, this).GetSpinnerData();
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        /*
        if(RssFeedItemSelector.GetInsrance().GetDesiredRssFeedItem() != null) {
            Date date;

            try {
                date = (Date) RssFeedItemSelector.GetInsrance().GetDesiredRssFeedItem().GetDescription().GetItem("Start Date").GetValue();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.get(calendar.MONTH);
        }

         */
    }

    public String formatDateElement(String element, Spinner spinner){
        if(spinner.getSelectedItem().toString().length() == 1){
            return "0" + spinner.getSelectedItem().toString();
        }
        return element = spinner.getSelectedItem().toString();
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.apply_button) {
            RssFeedItem desiredItem = new RssFeedItem();
            RssItemLocation itemLocation = new RssItemLocation(roadEditText.getText().toString(), junctionEditText.getText().toString());
            desiredItem.SetRssItemLocation(itemLocation);

            ArrayList<String> desiredTypes = new ArrayList<String>();

            if(roadworksCheckBox.isChecked())
                desiredTypes.add(RssFeedTypes.ROADWORK);
            if(plannedRoadworksCheckBox.isChecked())
                desiredTypes.add(RssFeedTypes.PLANNED_ROADWORK);
            if(incidentCheckBox.isChecked())
                desiredTypes.add(RssFeedTypes.CURRENT_INSIDENT);


            RssFeedItemSelector.GetInsrance().SetDesiredType(desiredTypes);

            Date desiredDate = new Date();

            String day = formatDateElement(daySpinner.getSelectedItem().toString(), daySpinner);
            String month = formatDateElement(monthSpinner.getSelectedItem().toString(), monthSpinner);
            String year = yearSpinner.getSelectedItem().toString();

            try {
                    desiredDate = new SimpleDateFormat("dd MM yyyy").parse(day + " " +
                        month + " " +
                        year);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Description desiredDescription = new Description();
            desiredDescription.AddDescriptionEntity(new DescriptionEntity("Start Date", desiredDate));
            desiredItem.SetItemDescription(desiredDescription);

            RssFeedItemSelector.GetInsrance().SetDesiredRssItem(desiredItem);

            // load new map activity
            Intent myIntent = new Intent(this, MapActivity.class);
            this.startActivity(myIntent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == monthSpinner.getId()) {

            int monthNum = 1;
            // get date value
            try{
                monthNum = Integer.parseInt(monthSpinner.getSelectedItem().toString());
            } catch (NumberFormatException e) {
            }

            ArrayAdapter<Integer> dayAdapter = new DaySpinnerContainer(monthNum, this).GetSpinnerData();
            daySpinner.setAdapter(dayAdapter);
            daySpinner.setOnItemSelectedListener(this);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
