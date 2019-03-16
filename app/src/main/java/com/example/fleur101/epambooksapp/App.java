package com.example.fleur101.epambooksapp;

import androidx.multidex.MultiDexApplication;
import androidx.appcompat.app.AppCompatDelegate;

import timber.log.Timber;

/**
 * Created by Assylkhanov Aslan on 15.03.2019.
 */
public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
