package com.example.fleur101.epambooksapp;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import androidx.appcompat.app.AppCompatDelegate;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.multidex.MultiDexApplication;
import timber.log.Timber;

/**
 * Created by Assylkhanov Aslan on 15.03.2019.
 */
public class App extends MultiDexApplication {


    private static BooksApi booksApi;
    private Retrofit retrofit;
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        booksApi = retrofit.create(BooksApi.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static BooksApi getBooksApi() {
        return booksApi;
    }
}
