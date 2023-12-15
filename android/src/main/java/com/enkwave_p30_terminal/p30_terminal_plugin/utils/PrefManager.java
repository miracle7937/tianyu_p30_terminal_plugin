package com.enkwave_p30_terminal.p30_terminal_plugin.utils;

import android.content.SharedPreferences;

import com.enkwave_p30_terminal.p30_terminal_plugin.networking.model.PrepResultRecord;
import com.google.gson.Gson;

public class PrefManager {
    private static final String PREF_NAME = "eTopCardLibrary";
    // shared pref mode
    int PRIVATE_MODE = 0;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public static final String PREP_DATA = "PREP_DATA";
    public static final String PREP_DATA_TIME = "PREP_DATA_TIME";
    public PrefManager() {
        pref = MyApplication.getINSTANCE().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void savePrepData(PrepResultRecord prepResultRecord) {
        editor.putString(PREP_DATA, new Gson().toJson(prepResultRecord));
        editor.commit();
    }

    public void savePrepTime( Long value) {
        System.out.println(" Prep Time ===============> "+ value);
        editor.putLong(PREP_DATA_TIME, value);
        editor.commit();
    }
    public Long getPrepTime() {
        return pref.getLong(PREP_DATA_TIME, 0);

    }
    public PrepResultRecord getPrepData() {
        return  new Gson().fromJson(pref.getString(PREP_DATA, ""),PrepResultRecord.class );
    }
}
