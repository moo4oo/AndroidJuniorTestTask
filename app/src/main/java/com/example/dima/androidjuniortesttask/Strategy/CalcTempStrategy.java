package com.example.dima.androidjuniortesttask.Strategy;

public class CalcTempStrategy {
    TempCalc tempCalc;

    public void setTempCalc(TempCalc tempCalc){
        this.tempCalc = tempCalc;
    }

    public String execute(float temp){
        return tempCalc.calcTemp(temp);
    }
}
