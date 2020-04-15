package com.example.mobileplatformdev;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.mobileplatformdev.SpinnerDataContainer;

import java.util.ArrayList;

public class MonthSpinnerContainer implements SpinnerDataContainer<Byte> {

    private Context context;

    public MonthSpinnerContainer(Context context){
        this.context = context;
    }

    @Override
    public ArrayAdapter<Byte> GetSpinnerData() {
        ArrayList<Byte> data = new ArrayList<Byte>(12);

        for(byte i = 0; i < 12; i++){
            data.add(i, (byte) (i + 1));
        }

        return new ArrayAdapter<Byte>(context, android.R.layout.simple_spinner_item, data);
    }
}
