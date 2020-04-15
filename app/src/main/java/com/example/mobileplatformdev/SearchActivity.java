package com.example.mobileplatformdev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner typeSpinner;
    private Spinner daySpinner;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private Button applyButton;

    private EditText roadEditText;
    private EditText junctionEditText;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        typeSpinner = (Spinner) findViewById(R.id.type_spinner);
        daySpinner = (Spinner) findViewById(R.id.day_spinner);
        monthSpinner = (Spinner) findViewById(R.id.month_spinner);
        yearSpinner = (Spinner) findViewById(R.id.year_spinner);
        applyButton = (Button) findViewById(R.id.apply_button);
        roadEditText = (EditText) findViewById(R.id.road_edit_text);
        junctionEditText = (EditText) findViewById(R.id.junction_edit_text);

        // set on click listener for apply button
        applyButton.setOnClickListener(this);

        // add elements to type spinner
        ArrayList<String> arrayList = new ArrayList<>(3);
        arrayList.add(0, RssFeedTypes.CURRENT_INSIDENT);
        arrayList.add(1, RssFeedTypes.ROADWORK);
        arrayList.add(2, RssFeedTypes.PLANNED_ROADWORK);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(arrayAdapter);
        typeSpinner.setOnItemSelectedListener(this);

        // add elements to day spinner
        ArrayAdapter<Integer> dayAdapter = new DaySpinnerContainer(1, this).GetSpinnerData();
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setOnItemSelectedListener(this);

        // add elements to month spinner
        ArrayAdapter<Byte> monthAdapter = new MonthSpinnerContainer(this).GetSpinnerData();
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setOnItemSelectedListener(this);

        // add elements to year spinner
        ArrayAdapter<Integer> yearAdapter = new YearSpinnerDataContainer(5, this).GetSpinnerData();
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.apply_button){
            RssFeedItem desiredItem = new RssFeedItem();
            RssItemLocation itemLocation = new RssItemLocation(roadEditText.getText().toString(), junctionEditText.getText().toString());
            desiredItem.SetRssItemLocation(itemLocation);

            String desiredType = typeSpinner.getSelectedItem().toString();

            RssFeedItemSelector.GetInsrance().SetDesiredRssItem(desiredItem);
            RssFeedItemSelector.GetInsrance().SetDesiredType(desiredType);

            Date desiredDate = new Date();

            try {
                    desiredDate = new SimpleDateFormat("dd mm yyyy").parse(daySpinner.getSelectedItem().toString() + " " +
                        monthSpinner.getSelectedItem().toString() + " " +
                        yearSpinner.getSelectedItem().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Description desiredDescription = new Description();
            desiredDescription.AddDescriptionEntity(new DescriptionEntity("Start Date", desiredDate));
            desiredItem.SetItemDescription(desiredDescription);


            // load new map activity
            Intent myIntent = new Intent(this, MapActivity.class);
            this.startActivity(myIntent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
