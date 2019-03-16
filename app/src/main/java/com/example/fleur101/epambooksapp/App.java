package com.example.fleur101.epambooksapp;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;
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

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
