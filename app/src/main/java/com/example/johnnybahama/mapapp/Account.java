package com.example.johnnybahama.mapapp;

public class Account {
    private String email;
    private int dateRangeMin;
    private int dateRangeMax;
    private boolean sendAnyNotifications;
    private boolean sendCommentNotifications;
    private boolean sendPinNotifications;


    public void Account(String email){
        dateRangeMax = 0;
        dateRangeMin = 0;;
        sendAnyNotifications = true;
        sendCommentNotifications = true;
        sendPinNotifications = true;
        email = email;
    }


    public void setEmail(String email) {
        this.email = email;
    }
}
