package com.example.dima.androidjuniortesttask.Strategy;

public class CalcFahrenheitTemp implements TempCalc {
    @Override
    public String calcTemp(float temp) {
        return (temp*9/5 + 32) + " F";
    }
}
