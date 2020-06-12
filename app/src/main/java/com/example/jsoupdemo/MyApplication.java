package com.example.jsoupdemo;

import android.app.Application;
import android.content.Context;

/**
 * application
 */
public class MyApplication extends Application {
    public static Context  mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
    }
}
