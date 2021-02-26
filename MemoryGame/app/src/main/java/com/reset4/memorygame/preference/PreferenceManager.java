package com.reset4.memorygame.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by eilkyam on 04.01.2018.
 */

public class PreferenceManager {
    private Activity activity;

    public PreferenceManager(Activity activity){
        setActivity(activity);
    }

    private Activity getActivity() {
        return activity;
    }

    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getIntegerValue(String key){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getInt(key, 0);
    }

    public void setIntegerValue(String key, int value){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
