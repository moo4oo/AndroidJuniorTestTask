package com.example.dima.androidjuniortesttask.Observer;


import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TempObserved implements Observed {
    private TextView textView;
    private float temp = 0;
    public TempObserved(TextView textView){
        this.textView = textView;
    }
    public void updateTemp(float temp){
        this.temp = temp;
        notifyObservers();
    }
    List<Observer> subscribers = new ArrayList<>();
    @Override
    public void addObserver(Observer observer) {
        this.subscribers.add(observer);

    }

    @Override
    public void removeObserver(Observer observer) {
        this.subscribers.remove(observer);

    }

    @Override
    public void notifyObservers() {
        for(Observer observer: subscribers){
            observer.handleEvent(textView, temp);
        }

    }
}
