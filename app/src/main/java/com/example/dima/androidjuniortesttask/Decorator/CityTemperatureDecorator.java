package com.example.dima.androidjuniortesttask.Decorator;

public class CityTemperatureDecorator implements Temperature {
    Temperature temperature;
    public CityTemperatureDecorator(Temperature temperature){
        this.temperature = temperature;
    }

    @Override
    public float getTemp() {
        return temperature.getTemp();
    }
}
