package com.example.mobileplatformdev;

import java.util.Date;

public class DescriptionEntity
{
    private String tag;
    private Object value;

    public DescriptionEntity(String tag, Object value) {
        this.tag = tag;
        this.value = value;
    }

    public String GetTag() {return tag; }

    public Object GetValue() { return value; }
}
