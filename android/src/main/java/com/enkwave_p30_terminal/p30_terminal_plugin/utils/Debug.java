package com.enkwave_p30_terminal.p30_terminal_plugin.utils;

import android.util.Log;


public class Debug {
    private static final String TAG = Debug.class.getSimpleName();
    static public void print(Object value) {
        Log.d(TAG, String.valueOf(value) );

    }
}