package com.cuining.myapplication;

import android.app.Application;

import com.meb.tracker.TrackerSDK;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TrackerSDK.getInstance().init(this, "123");
    }
}
