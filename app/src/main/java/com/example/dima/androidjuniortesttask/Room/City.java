package com.example.dima.androidjuniortesttask.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class City {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int cityId;

    public void setCityType(String cityType) {
        this.cityType = cityType;
    }

    @ColumnInfo(name = "cityType")
    private String cityType;
    @ColumnInfo(name = "cityName")
    private String cityName;
    @ColumnInfo(name = "tempSummer")
    private float tempSummer;
    @ColumnInfo(name = "tempWinter")
    private float tempWinter;
    @ColumnInfo(name = "tempAutumn")
    private float tempAutum;

    public City(String cityType, String cityName, float tempSummer, float tempWinter, float tempAutum, float tempSpring) {
        this.cityType = cityType;
        this.cityName = cityName;
        this.tempSummer = tempSummer;
        this.tempWinter = tempWinter;
        this.tempAutum = tempAutum;
        this.tempSpring = tempSpring;
    }

    @NonNull
    public int getCityId() {
        return cityId;
    }

    public void setCityId(@NonNull int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public float getTempSummer() {
        return tempSummer;
    }

    public void setTempSummer(float tempSummer) {
        this.tempSummer = tempSummer;
    }

    public float getTempWinter() {
        return tempWinter;
    }

    public void setTempWinter(float tempWinter) {
        this.tempWinter = tempWinter;
    }


    public float getTempSpring() {
        return tempSpring;
    }

    public void setTempSpring(float tempSpring) {
        this.tempSpring = tempSpring;
    }

    @ColumnInfo(name = "tempSpring")
    private float tempSpring;

    public String getCityType() {
        return cityType;
    }

    public float getTempAutum() {
        return tempAutum;
    }

    public void setTempAutum(float tempAutum) {
        this.tempAutum = tempAutum;
    }
}
