package com.reset4.passlock.ui;

import android.content.Context;

/**
 * Created by ilkery on 31.12.2016.
 */
public class UIHelper {

    public static int ScrollPosition = 0;

    public static float getDensityPixel(Context context, int val){
        return context.getResources().getDisplayMetrics().density * 17;
    }

    public static int getColor(Context context, int colorResourceId){
        return context.getResources().getColor(colorResourceId);
    }
}
