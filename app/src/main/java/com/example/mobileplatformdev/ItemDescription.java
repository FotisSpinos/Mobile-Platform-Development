package com.example.mobileplatformdev;

import java.util.Date;


public class ItemDescription
{
    protected String description;

    public void SetDescription(String description)
    {
        this.description = description;
    }

    public String GetDescription()
    {
        return description;
    }

    // GETTERS

    public Date GetStartDate() { return null; }

    public Date GetEndDate() { return null; }

    //SETTERS

    public void SetStartDate(Date date) { }

    public void SetEndDate(Date date) { }
}

// Idemdescription
    // has a linked list of descEntities

// a descEntity has a string and a value
/*
template class descEntity<class T>
    private T value;
    private String tag;

    void SetTag(String tag)
    T GetValue() {}

example
descEntitis to use:
Works -> valueType: string, tag: works
LaneClosed -> valueType: boolean, tag: laneClosed

 */