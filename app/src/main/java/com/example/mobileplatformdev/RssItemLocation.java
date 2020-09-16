package com.example.mobileplatformdev;

import android.util.Log;

import java.util.LinkedList;

public class RssItemLocation {

    private String road;
    private LinkedList<String> junctions;

    public RssItemLocation(String road, String junction){
        this.road = road;
        this.junctions = new LinkedList<String>();
        junctions.push(junction);
    }

    public RssItemLocation(String road, LinkedList<String> junctions){
        this.road = road;
        this.junctions = junctions;
    }

    public RssItemLocation(String xmlData) {

        // Initialize junctionNumbers
        junctions = new LinkedList<String>();

        road = "";
        int roadIndex = 1;

        // store road
        while(road == ""){
            if(xmlData.charAt(roadIndex) == ' ' || roadIndex == xmlData.length() - 1){
                road = xmlData.substring(0, roadIndex);
                break;
            }
            roadIndex++;
        }

        // store junctions
        for (int i = roadIndex; i < xmlData.length(); i++) {
            if (xmlData.charAt(i) == 'J') {
                if (i + 8 <= xmlData.length() && xmlData.substring(i, i + 8).equalsIgnoreCase("Junction")) {
                    StoreJunc(i, 9, xmlData);
                }
                else if (i + 3 <= xmlData.length() && xmlData.substring(i, i + 3).equalsIgnoreCase("Jct")) {
                    StoreJunc(i, 4, xmlData);
                }
                else {
                    int endIndex = FindJunNumEndIndex(i, xmlData);
                    StoreJuncFrontNum(i + 1, endIndex, xmlData);
                }
            }
        }
    }

    private void StoreJunc(int start, int wordOffset, String xmlData){
        int endIndex = FindJunNumEndIndex(start + wordOffset, xmlData);
        if(!StoreJuncFrontNum(start + wordOffset, endIndex, xmlData)) {
            String prev = GetWordBeforeJunc(start, xmlData);
            junctions.push(prev);
        }
    }

    private String GetWordBeforeJunc(int start, String xmlData){
        boolean spaceIndex = false;
        int endIndex = 0;
        int startIndex = 0;

        for(int y = start; y >= 0; y--) {

            if(!spaceIndex && xmlData.charAt(y) == ' ') {
                endIndex = y;
                spaceIndex = true;
            }


            else if(xmlData.charAt(y) == '-')
                startIndex = y;

            else if(xmlData.charAt(y) == ' ' ||
                    y == xmlData.length() - 1) {
                startIndex = y + 1;
                return xmlData.substring(startIndex, endIndex);
            }
        }

        String output = xmlData.substring(startIndex, endIndex);
        return output;
    }

    // gives the index of the number located after the junction keyword
    private int FindJunNumEndIndex(int start, String xmlData) {
        int spaceIndex = start;

        for(int y = start; y < xmlData.length(); y++){
            char debugChar = xmlData.charAt(y);
            if(xmlData.charAt(y) == ' ' ||
                    y == xmlData.length() - 1 ||
                    xmlData.charAt(y) == '-'){
                return y;
            }
        }

        return start;
    }

    private boolean StoreJuncFrontNum(int start, int end, String xmlData){

        if(start > end || xmlData.length() <= end)
            return false;

        Log.println(Log.ERROR, "ParsingError", xmlData);

        try{
            String junctionText = xmlData.substring(start, end);
            if(junctionText.equals("")) {
                junctionText += xmlData.charAt(xmlData.length()-1);
            }
            Byte junctionNum = Byte.parseByte(junctionText);
            junctions.push(junctionText);
            return true;
        } catch (NumberFormatException e) {
            Log.println(Log.ERROR, "ParsingError", "Junction could not be added");
            e.printStackTrace();
        }

        return false;
    }

    // ** GETTERS **

    public String GetRoad(){
        return road;
    }

    public LinkedList<String> GetJunctions(){return junctions;}
}
