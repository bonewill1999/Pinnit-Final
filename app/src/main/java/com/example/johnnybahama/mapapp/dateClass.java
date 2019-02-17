package com.example.johnnybahama.mapapp;

import java.util.ArrayList;

public class dateClass {

    // 07032018

    private String dateString;
    private int days;
    private int months;
    private int years;
    private int totalDays;


    public dateClass(String date){
        if(date.length() != 8){
            this.years = 0;
            this.months = 0;
            this.years = 0;
            this.totalDays = 0;

        }
        else{

            String daysTemp = String.valueOf(date.charAt(0)) + String.valueOf(date.charAt(1));
            String monthsTemp = String.valueOf(date.charAt(2)) + String.valueOf(date.charAt(3));
            String yearsTemp = String.valueOf(date.charAt(4)) + String.valueOf(date.charAt(5)) + String.valueOf(date.charAt(6))+ String.valueOf(date.charAt(7));
            this.days = Integer.parseInt(daysTemp);
            this.months = Integer.parseInt(monthsTemp);
            this.years = Integer.parseInt(yearsTemp);

        }


    }



    public void setDays(int days){
        this.days = days;
    }

    public void setMonths(int months){
        this.months = months;
    }

    public void setYears(int years){
        this.years = years;
    }

    public int getDays(){
        return this.days;
    }
    public int getYears(){
        return this.years;
    }
    public int getMonths(){
        return this.months;
    }

    public int getOldness(dateClass currentDate){

        int tempDays = currentDate.days - this.days;
        int tempMonths = currentDate.months - this.months;
        int tempYears = currentDate.years - this.years;

        int totalDaysSince = (tempYears*365) + (tempMonths*30) + tempDays;
        return totalDaysSince;


    }

}
