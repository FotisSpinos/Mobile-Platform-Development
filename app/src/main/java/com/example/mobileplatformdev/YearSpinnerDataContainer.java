package com.example.mobileplatformdev;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class YearSpinnerDataContainer implements SpinnerDataContainer<Integer>{

    private int yearRange;
    private Context context;

    YearSpinnerDataContainer(int yearRange, Context context){
        this.yearRange = yearRange;
        this.context = context;
    }

    @Override
    public ArrayAdapter<Integer> GetSpinnerData() {

        ArrayList<Integer> data = new ArrayList<Integer>(yearRange);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);


        for(int i = 0; i < yearRange * 2; i++){
            data.add(i, currentYear - yearRange + i);
        }

        return new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_item, data);
    }
}
