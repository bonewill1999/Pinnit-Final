package com.example.johnnybahama.mapapp;

import java.util.ArrayList;

public class PinModel {

    private ArrayList<Pin> Pins = new ArrayList<Pin>();
    private Account currentAccount;





    public void addPin(Pin pin){
        Pins.add(pin);
    }

    public void removePin(Pin pin){
        Pins.remove(pin);
    }

    public ArrayList<Pin> getPins(){
        return Pins;
    }








}
