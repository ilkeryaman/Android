package com.reset4.memorygame.ui.font;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by eilkyam on 06.09.2015.
 */
public class Font {
    private final static String PorkysBoldFontPath = "fonts/porkh.ttf";
    private final static String PorkysFontPath = "fonts/porkys.ttf";
    private final static String DestroyEarthFontPath = "fonts/destroyearthbb.ttf";
    public static Typeface PorkysBoldFont = null;
    public static Typeface PorkysFont = null;
    public static Typeface DestroyEarthFont = null;

    public static void loadFont(Context context) {
        PorkysBoldFont = Typeface.createFromAsset(context.getAssets(), PorkysBoldFontPath);
        PorkysFont = Typeface.createFromAsset(context.getAssets(), PorkysFontPath);
        DestroyEarthFont = Typeface.createFromAsset(context.getAssets(), DestroyEarthFontPath);
    }
}
