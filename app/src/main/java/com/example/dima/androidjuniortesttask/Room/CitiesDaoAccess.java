package com.example.dima.androidjuniortesttask.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CitiesDaoAccess {
    @Insert
    void insertCity(City city);

    @Query("SELECT * FROM City")
    List<City> getAllCities();

    @Update
    void updateCity(City city);
    @Delete
    void deleteCity(City city);
}
