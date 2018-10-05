package com.example.dima.androidjuniortesttask.Factory;

import java.util.ArrayList;

public class TypeFactory {
    public static ArrayList<String> smallCities;
    public static ArrayList<String> mediumCities;
    public static ArrayList<String> bigCities;

    public static Type getType(String criteria){
        if(smallCities.contains(criteria)){
            return new Small();
        }else if(mediumCities.contains(criteria)){
            return new Medium();
        }else if(bigCities.contains(criteria)){
            return new Big();
        }else return null;
    }
}
