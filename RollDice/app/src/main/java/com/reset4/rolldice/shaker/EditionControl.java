package com.reset4.rolldice.shaker;

import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.reset4.rolldice.DiceActivity;
import com.reset4.rolldice.R;
import com.reset4.rolldice.color.Color;

/**
 * Created by eilkyam on 31.05.2015.
 */
public class EditionControl {

    private static boolean isProfessionalEdition()
    {
        return false;
    }

    public static boolean isAdFree() { return false || isProfessionalEdition(); }

    public static int getBackgroundNumber(DiceActivity diceActivity)
    {
        int backgroundNumber = Color.Blue;
        //if(isProfessionalEdition()) {
            String preferencesName = "backgroundNumber";
            SharedPreferences prefSettings = diceActivity.getSharedPreferences(preferencesName, diceActivity.MODE_PRIVATE);
            backgroundNumber = prefSettings.getInt(preferencesName, Color.Blue);
        //}
        return backgroundNumber;
    }

    public static int getDiceColorNumber(DiceActivity diceActivity)
    {
        int diceColorNumber = Color.Red;
        //if(isProfessionalEdition()) {
            String preferencesName = "diceColor";
            SharedPreferences prefSettings = diceActivity.getSharedPreferences(preferencesName, diceActivity.MODE_PRIVATE);
            diceColorNumber = prefSettings.getInt(preferencesName, Color.Red);
        //}
        return diceColorNumber;
    }

    public static void shake(DiceActivity diceActivity, float deltaX, float deltaY, float deltaZ, float shakeThreshold){
        //if(isProfessionalEdition()) {
            // if the change is below 2, it is just plain noise
            if (deltaX < 2)
                deltaX = 0;
            if (deltaY < 2)
                deltaY = 0;

            if (deltaX > shakeThreshold || deltaY > shakeThreshold || deltaZ > shakeThreshold) {
                diceActivity.rollingManager.handleShake();
            }
        //}
    }

    public static void showHideMenuItems(Menu menu)
    {
        if(!isProfessionalEdition())
        {
            //MenuItem backgroundSelection = (MenuItem)menu.findItem(R.id.backgroundColor);
            //MenuItem diceColorSelection = (MenuItem) menu.findItem(R.id.diceColor);
            MenuItem buyProSelection = (MenuItem) menu.findItem(R.id.buyPro);
            //backgroundSelection.setVisible(false);
            //diceColorSelection.setVisible(false);
            buyProSelection.setVisible(true);
        }
        else
        {
            MenuItem buyProSelection = (MenuItem) menu.findItem(R.id.buyPro);
            buyProSelection.setVisible(false);
        }
    }

    public static void cheatAllowance(DiceActivity diceActivity)
    {
        if(isProfessionalEdition())
        {
            ImageView backgroundImageView = (ImageView)diceActivity.findViewById(R.id.backgroundImageView);
            CheatControl cheatControl = new CheatControl(diceActivity, backgroundImageView);
        }
    }


}
