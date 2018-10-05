package com.example.dima.androidjuniortesttask.Decorator;

import android.util.Log;

public class CityTemperatureLog extends CityTemperatureDecorator {


    public CityTemperatureLog(Temperature temperature) {
        super(temperature);
    }
    public void log(){
        Log.i("temp", super.getTemp()+"");
    }

    @Override
    public float getTemp() {
        log();
        return super.getTemp();
    }
}
