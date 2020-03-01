package com.example.mobileplatformdev;

import android.util.Log;

public class Point {
    private double x;
    private double y;

    public Point(String pointString)
    {
        // get the index of the space to seperate the two numbers
        int spaceIndex = pointString.indexOf(" ");

        // if the space is found
        if(spaceIndex != -1)
        {
            String xString = pointString.substring(0, spaceIndex - 1);
            String yString = pointString.substring(spaceIndex + 1, pointString.length());

            // parse the strings into doubles
            try {
                x = Double.parseDouble(xString);
                y = Double.parseDouble(yString);
            }
            catch(Exception e) {
                Log.e("Parsing Error","Parsing point data failed");
            }
        }
    }

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Point() {}


    /* GETTERS */
    public double GetX()
    {
        return x;
    }

    public double GetY()
    {
        return y;
    }
}
