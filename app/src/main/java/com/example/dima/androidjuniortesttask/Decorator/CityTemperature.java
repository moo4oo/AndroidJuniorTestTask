package com.example.dima.androidjuniortesttask.Decorator;

import com.example.dima.androidjuniortesttask.Room.City;

public class CityTemperature implements Temperature {
    public float temperature;
    public CityTemperature(float temp){
        temperature = temp;
    }
    @Override
    public float getTemp() {
        return temperature;
    }
}
