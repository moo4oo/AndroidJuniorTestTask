package com.example.dima.androidjuniortesttask.Strategy;

public class CalcKelvinTemp implements TempCalc {
    @Override
    public String calcTemp(float temp) {
        return temp + 273.15f + " K";
    }
}
