package com.reset4.passlock.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ilkery on 8.01.2017.
 */
public class SharedPrefenceManager {
    private static String SharedPrefenceName = "r4PassLock";

    public static long getSharedPreferenceValue(Context context, String key){
        SharedPreferences prefSettings = context.getSharedPreferences(SharedPrefenceName, context.MODE_PRIVATE);
        return prefSettings.getLong(key, 0);
    }

    public static void setSharedPreferenceValue(Context context, String key, long value){
        SharedPreferences prefSettings = context.getSharedPreferences(SharedPrefenceName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefSettings.edit();
        editor.putLong(key, value);
        editor.commit();
    }
}
