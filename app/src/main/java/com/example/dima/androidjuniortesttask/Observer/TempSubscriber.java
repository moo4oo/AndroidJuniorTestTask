package com.example.dima.androidjuniortesttask.Observer;

import android.support.design.widget.Snackbar;
import android.widget.TextView;

public class TempSubscriber implements Observer {

    @Override
    public void handleEvent(TextView view, float temp) {


        Snackbar snackbar = Snackbar
                .make(view, "Средняя температура за выбранный сезон: " + temp, Snackbar.LENGTH_LONG);
        snackbar.show();


    }
}
