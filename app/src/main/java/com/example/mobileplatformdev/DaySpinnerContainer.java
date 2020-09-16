package com.example.mobileplatformdev;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class DaySpinnerContainer implements SpinnerDataContainer<Integer> {

    private ArrayList<Integer> data;
    private Context context;

    public DaySpinnerContainer(int month, Context context){

        this.context = context;

        if(month <= 0 || month > 12) {
            throw new IllegalArgumentException(String.format("%s %n", "The month retrieved is invalid. month value:", month));
        }

        else if(month == 2){
            FillContents(28);
        }

        else{
            if(month % 2 == 0)
                FillContents(30);
            else
                FillContents(31);
        }
    }

    private void FillContents(int maxNumber){
        data = new ArrayList<Integer>(maxNumber);

        for(int i = 0; i <= maxNumber - 1; i++){
            data.add(i, i + 1);
        }
    }

    @Override
    public ArrayAdapter<Integer> GetSpinnerData() {
        return new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_item, data);
    }
}
