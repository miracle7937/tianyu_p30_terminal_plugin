package com.enkwave_p30_terminal.p30_terminal_plugin.utils;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static MyApplication INSTANCE;

    public static MyApplication getINSTANCE(){
        return INSTANCE;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        Log.d("welldone", "welldone");
    }
}
