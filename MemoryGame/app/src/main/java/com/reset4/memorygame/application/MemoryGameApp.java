package com.reset4.memorygame.application;

import android.app.Application;
import android.content.Context;

import com.reset4.memorygame.ui.font.Font;

/**
 * Created by eilkyam on 29.12.2017.
 */

public class MemoryGameApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        loadFont();

    }

    private void loadFont(){
        Font.loadFont(this);
    }

    public static Context getAppContext(){
        return context;
    }
}
