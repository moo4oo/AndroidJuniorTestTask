package com.example.dima.androidjuniortesttask.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {City.class}, version = 1)
public abstract class CitiesDatabase extends RoomDatabase {
    public abstract CitiesDaoAccess citiesDaoAccess();
}
