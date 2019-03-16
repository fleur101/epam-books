package com.example.fleur101.epambooksapp;

import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

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
