package com.example.textviewfinder;

import com.amida.easydb.EasyDb;

import android.app.Application;

public class SampleApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        
        EasyDb.getInstance().init(this, "secretkey");
    }
}
