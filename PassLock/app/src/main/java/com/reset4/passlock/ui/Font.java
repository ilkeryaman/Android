package com.reset4.passlock.ui;

import android.content.Context;
import android.graphics.Typeface;

import com.reset4.fourwork.engine.general.FourContext;

/**
 * Created by ilkery on 31.12.2016.
 */
public class Font {
    public final static String RalewayMedium = "1";
    public final static String RalewaySemiBold = "2";
    private final static String RalewayMediumPath = "fonts/Raleway-Medium.ttf";
    private final static String RalewaySemiBoldPath = "fonts/Raleway-SemiBold.ttf";
    public static Typeface RalewayMediumFont = null;
    public static Typeface RalewaySemiBoldFont = null;

    public static void loadFont(FourContext fourContext) {
        if(RalewayMediumFont == null) {
            RalewayMediumFont = Typeface.createFromAsset(fourContext.getApplicationContext().getAssets(), RalewayMediumPath);
        }

        if(RalewaySemiBoldFont == null){
            RalewaySemiBoldFont = Typeface.createFromAsset(fourContext.getApplicationContext().getAssets(), RalewaySemiBoldPath);
        }
    }
}
