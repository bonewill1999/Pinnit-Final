package com.example.johnnybahama.mapapp;

import android.content.Context;
import android.widget.Toast;

public class propertyValue {

    public Double doubleValue;
    public String stringValue;

    public propertyValue(Double doubleValue){
        this.doubleValue = doubleValue;
        this.stringValue = null;
    }
    public propertyValue(String stringValue){
        this.stringValue = stringValue;
        this.doubleValue = null;


    }

    public Double getDoubleValue(){
        return this.doubleValue;
    }

    public String getStringValue(){
        return this.stringValue;
    }

    public void setDoubleValue(Double doubleValue){
        this.doubleValue = doubleValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}
