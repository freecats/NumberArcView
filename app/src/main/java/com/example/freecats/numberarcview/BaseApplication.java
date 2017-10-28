package com.example.freecats.numberarcview;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    private static Context gContext;
    public static BaseApplication instance = null;

    public static BaseApplication getInstance() {
        if (instance == null) {
            synchronized (BaseApplication.class) {
                if (instance == null) {
                    instance = new BaseApplication();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        gContext = this.getApplicationContext();
        instance = this;

    }


    public static Context getGlobalContext() {
        return gContext;
    }

}
