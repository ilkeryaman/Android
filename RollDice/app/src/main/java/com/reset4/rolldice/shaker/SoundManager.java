package com.reset4.rolldice.shaker;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;

import com.reset4.rolldice.DiceActivity;
import com.reset4.rolldice.R;

/**
 * Created by eilkyam on 28.05.2015.
 */
public class SoundManager {
    SoundPool dice_sound;
    int shakeDiceSoundId, throwDiceSoundId;		//Used to control sound stream return by SoundPool
    DiceActivity diceActivity;

    public SoundManager(DiceActivity activity)
    {
        diceActivity = activity;
        //load dice sound
        dice_sound = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
        shakeDiceSoundId = dice_sound.load(diceActivity, R.raw.shakedice, 1);
        throwDiceSoundId = dice_sound.load(diceActivity, R.raw.throwdice, 1);
    }

    public void shakeDiceSound(){
        if(isSoundOn()) {
            dice_sound.play(shakeDiceSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    public void throwDiceSound(){
        if(isSoundOn()) {
            dice_sound.play(throwDiceSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    public boolean isSoundOn()
    {
        String preferencesName = "isSoundOn";
        SharedPreferences prefSettings =  diceActivity.getSharedPreferences(preferencesName, diceActivity.MODE_PRIVATE);
        return prefSettings.getBoolean(preferencesName, true);
    }

    public void soundOn()
    {
        setSoundOnOff(true);
    }

    public void soundOff()
    {
        setSoundOnOff(false);
    }

    private void setSoundOnOff(boolean isOn){
        String preferencesName = "isSoundOn";
        SharedPreferences prefSettings = diceActivity.getSharedPreferences(preferencesName, diceActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefSettings.edit();
        editor.putBoolean(preferencesName, isOn);
        editor.commit();
    }
}
