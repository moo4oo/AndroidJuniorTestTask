package com.example.dima.androidjuniortesttask.Strategy;

public class CalcCelsiusTemp implements TempCalc {
    @Override
    public String calcTemp(float temp) {
        return temp + " C";
    }
}
