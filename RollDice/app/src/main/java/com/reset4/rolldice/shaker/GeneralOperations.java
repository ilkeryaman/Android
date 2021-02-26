package com.reset4.rolldice.shaker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ImageView;

import com.reset4.rolldice.DiceActivity;
import com.reset4.rolldice.R;
import com.reset4.rolldice.color.Color;

/**
 * Created by eilkyam on 28.05.2015.
 */
public class GeneralOperations {

    public static void setBackgroundImage(DiceActivity activity, int which)
    {
        ImageView backgroundImageView = (ImageView) activity.findViewById(R.id.backgroundImageView);
        int resourceId = getBackgroundResourceId(activity, which);
        setBackgroundNumber(activity, which);
        backgroundImageView.setImageResource(resourceId);
        backgroundImageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    private static int getBackgroundNumber(DiceActivity diceActivity)
    {
        return EditionControl.getBackgroundNumber(diceActivity);
    }

    private static int getBackgroundResourceId(DiceActivity activity, int backgroundNumber)
    {
        int resourceId = 0;

        if(backgroundNumber == -1)
        {
            backgroundNumber = getBackgroundNumber(activity);
        }

        switch (backgroundNumber)
        {
            case Color.Blue:
                resourceId = R.drawable.bluebackground;
                break;
            case Color.Red:
                resourceId = R.drawable.redbackground;
                break;
            case Color.Green:
                resourceId = R.drawable.greenbackground;
                break;
            case Color.Black:
                resourceId = R.drawable.blackbackground;
                break;
            default:
                break;
        }

        return resourceId;
    }

    private static void setBackgroundNumber(DiceActivity diceActivity, int backgroundNumber)
    {
        if(isValidNumber(backgroundNumber)) {
            String preferencesName = "backgroundNumber";
            SharedPreferences prefSettings = diceActivity.getSharedPreferences(preferencesName, diceActivity.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefSettings.edit();
            editor.putInt(preferencesName, backgroundNumber);
            editor.commit();
        }
    }

    public static void setDiceColor(DiceActivity diceActivity, int which)
    {
        int resourceId = getDiceColorResourceId(diceActivity, which);
        setDiceColorNumber(diceActivity, which);
        diceActivity.dicePicture1.setImageResource(resourceId);
        diceActivity.dicePicture2.setImageResource(resourceId);
    }

    private static int getDiceColorResourceId(DiceActivity activity, int diceColorNumber)
    {
        int resourceId = 0;

        if(diceColorNumber == -1)
        {
            diceColorNumber = getDiceColorNumber(activity);
        }

        activity.diceColor = diceColorNumber;

        switch (diceColorNumber)
        {
            case Color.Blue:
                resourceId = R.drawable.dice3droll_blue;
                break;
            case Color.Red:
                resourceId = R.drawable.dice3droll;
                break;
            case Color.Green:
                resourceId = R.drawable.dice3droll_green;
                break;
            case Color.Black:
                resourceId = R.drawable.dice3droll_black;
                break;
            case Color.Yellow:
                resourceId = R.drawable.dice3droll_yellow;
                break;
            default:
                break;
        }

        return resourceId;
    }

    private static int getDiceColorNumber(DiceActivity diceActivity)
    {
        return EditionControl.getDiceColorNumber(diceActivity);
    }

    private static void setDiceColorNumber(DiceActivity diceActivity, int diceColorNumber)
    {
        if(isValidNumber(diceColorNumber)) {
            String preferencesName = "diceColor";
            SharedPreferences prefSettings = diceActivity.getSharedPreferences(preferencesName, diceActivity.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefSettings.edit();
            editor.putInt(preferencesName, diceColorNumber);
            editor.commit();
        }
    }

    private static boolean isValidNumber(int number)
    {
        return number != -1;
    }

    public static void goToHomePage(DiceActivity activity)
    {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(a);
    }

    public static int getDiceResourceId(DiceActivity diceActivity, int dice)
    {
        int resourceId = 0;
        switch(dice) {
            case 1:
                if(diceActivity.diceColor == Color.Red)
                    resourceId = R.drawable.one;
                else if(diceActivity.diceColor == Color.Black)
                    resourceId = R.drawable.one_black;
                else if(diceActivity.diceColor == Color.Blue)
                    resourceId = R.drawable.one_blue;
                else if(diceActivity.diceColor == Color.Green)
                    resourceId = R.drawable.one_green;
                else if(diceActivity.diceColor == Color.Yellow)
                    resourceId = R.drawable.one_yellow;
                break;
            case 2:
                if(diceActivity.diceColor == Color.Red)
                    resourceId = R.drawable.two;
                else if(diceActivity.diceColor == Color.Black)
                    resourceId = R.drawable.two_black;
                else if(diceActivity.diceColor == Color.Blue)
                    resourceId = R.drawable.two_blue;
                else if(diceActivity.diceColor == Color.Green)
                    resourceId = R.drawable.two_green;
                else if(diceActivity.diceColor == Color.Yellow)
                    resourceId = R.drawable.two_yellow;
                break;
            case 3:
                if(diceActivity.diceColor == Color.Red)
                    resourceId = R.drawable.three;
                else if(diceActivity.diceColor == Color.Black)
                    resourceId = R.drawable.three_black;
                else if(diceActivity.diceColor == Color.Blue)
                    resourceId = R.drawable.three_blue;
                else if(diceActivity.diceColor == Color.Green)
                    resourceId = R.drawable.three_green;
                else if(diceActivity.diceColor == Color.Yellow)
                    resourceId = R.drawable.three_yellow;
                break;
            case 4:
                if(diceActivity.diceColor == Color.Red)
                    resourceId = R.drawable.four;
                else if(diceActivity.diceColor == Color.Black)
                    resourceId = R.drawable.four_black;
                else if(diceActivity.diceColor == Color.Blue)
                    resourceId = R.drawable.four_blue;
                else if(diceActivity.diceColor == Color.Green)
                    resourceId = R.drawable.four_green;
                else if(diceActivity.diceColor == Color.Yellow)
                    resourceId = R.drawable.four_yellow;
                break;
            case 5:
                if(diceActivity.diceColor == Color.Red)
                    resourceId = R.drawable.five;
                else if(diceActivity.diceColor == Color.Black)
                    resourceId = R.drawable.five_black;
                else if(diceActivity.diceColor == Color.Blue)
                    resourceId = R.drawable.five_blue;
                else if(diceActivity.diceColor == Color.Green)
                    resourceId = R.drawable.five_green;
                else if(diceActivity.diceColor == Color.Yellow)
                    resourceId = R.drawable.five_yellow;
                break;
            case 6:
                if(diceActivity.diceColor == Color.Red)
                    resourceId = R.drawable.six;
                else if(diceActivity.diceColor == Color.Black)
                    resourceId = R.drawable.six_black;
                else if(diceActivity.diceColor == Color.Blue)
                    resourceId = R.drawable.six_blue;
                else if(diceActivity.diceColor == Color.Green)
                    resourceId = R.drawable.six_green;
                else if(diceActivity.diceColor == Color.Yellow)
                    resourceId = R.drawable.six_yellow;
                break;
            default:
        }

        return resourceId;
    }
}
